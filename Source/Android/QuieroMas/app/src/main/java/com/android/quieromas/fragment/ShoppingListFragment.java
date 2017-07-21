package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.ChangePasswordActivity;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.adapter.RecipeRecyclerViewAdapter;
import com.android.quieromas.adapter.ShoppingListRecyclerViewAdapter;
import com.android.quieromas.helper.AgeHelper;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.DevelopmentItem;
import com.android.quieromas.model.planDeNutricion.DiaPlanNutricion;
import com.android.quieromas.model.receta.IngredienteListaSemanal;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.model.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ShoppingListFragment extends BaseFragment {

    FirebaseDatabaseHelper firebaseDatabaseHelper;
    User user;
    int hmSize;
    HashMap<String,Integer> ingedients;
    ArrayList<String> ingredientsToShow;
    RecyclerView rvList;
    Button btnEmail;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Lista de compras semanal");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList = (RecyclerView) view.findViewById(R.id.shopping_list_rv);
        btnEmail = (Button) view.findViewById(R.id.shopping_list_btn_mail);

        ingedients = new HashMap<>();

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getCurrentUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                AgeHelper ageHelper = new AgeHelper();
                int planWeekStartDay = ageHelper.getPlanWeekStartDay(user.bebe.fechaDeNacimiento);
                firebaseDatabaseHelper.getPlanByAgeReference().orderByKey().startAt(Integer.toString(planWeekStartDay)).endAt(Integer.toString(planWeekStartDay + 6)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator< HashMap<String,DiaPlanNutricion>> t = new GenericTypeIndicator< HashMap<String,DiaPlanNutricion>>() {};
                        HashMap<String,DiaPlanNutricion> recipeHm = dataSnapshot.getValue(t);
                        HashMap<String,Integer> recipes = new HashMap<>();
                        for (Map.Entry<String, DiaPlanNutricion> entry : recipeHm.entrySet()) {
                            String key = entry.getKey();
                            DiaPlanNutricion value = entry.getValue();
                            if(recipes.containsKey(value.getAlmuerzo().getReceta())){
                                recipes.put(value.getAlmuerzo().getReceta(),recipes.get(value.getAlmuerzo().getReceta()) +1);
                            }else{
                                recipes.put(value.getAlmuerzo().getReceta(),1);
                            }
                            if(recipes.containsKey(value.getCena().getReceta())){
                                recipes.put(value.getCena().getReceta(),recipes.get(value.getCena().getReceta()) +1);
                            }else{
                                recipes.put(value.getCena().getReceta(),1);
                            }
                        }
                        hmSize = recipes.size();
                        for (final Map.Entry<String, Integer> entry : recipes.entrySet()) {
                            firebaseDatabaseHelper.getRecipeReference(entry.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    HashMap<String,Integer> recipeIngredientHm = dataSnapshot.getValue(Receta.class).getIngredientesLista();
                                    //iterarlos e ir metiendolos en otro hashmap con la cantidad multiplicada por el value de la entry
                                    //si ya estan definidos, sumarlos
                                    for (Map.Entry<String, Integer> ingrediententry : recipeIngredientHm.entrySet()) {
                                        int value = ingrediententry.getValue() * entry.getValue();
                                        if(ingedients.containsKey(ingrediententry.getKey())){
                                            ingedients.put(ingrediententry.getKey(),ingedients.get(ingrediententry.getKey()) + value);
                                        }else{
                                            ingedients.put(ingrediententry.getKey(),value);
                                        }
                                    }
                                    hmSize--;
                                    if(hmSize == 0){
                                        //query ingredients
                                        queryIngredients();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void queryIngredients(){
        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getIngredientsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator< HashMap<String,IngredienteListaSemanal>> t = new GenericTypeIndicator< HashMap<String,IngredienteListaSemanal>>() {};
                HashMap<String,IngredienteListaSemanal> ingredienteListaSemanalHm = dataSnapshot.getValue(t);

                ingredientsToShow = new ArrayList<String>();
                for (Map.Entry<String, Integer> ingredient : ingedients.entrySet()) {
                    if(ingredienteListaSemanalHm.containsKey(ingredient.getKey())){
                        ingredientsToShow.add(ingredient.getValue() + " " + ingredienteListaSemanalHm.get(ingredient.getKey()).getNombre());
                    }
                }
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateUI(){
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(new ShoppingListRecyclerViewAdapter(ingredientsToShow));
    }
}
