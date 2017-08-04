package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.quieromas.R;
import com.android.quieromas.activity.ChangePasswordActivity;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.adapter.RecipeRecyclerViewAdapter;
import com.android.quieromas.adapter.ShoppingListRecyclerViewAdapter;
import com.android.quieromas.api.FirebaseFunctionApi;
import com.android.quieromas.api.ServiceFactory;
import com.android.quieromas.helper.AgeHelper;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.DevelopmentItem;
import com.android.quieromas.model.api.ShoppingListParams;
import com.android.quieromas.model.planDeNutricion.DiaPlanNutricion;
import com.android.quieromas.model.receta.IngredienteListaSemanal;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.model.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ShoppingListFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    public static final String DATE_TEXT = "Para recetas de la semana del ";
    FirebaseDatabaseHelper firebaseDatabaseHelper;
    User user;
    int hmSize;
    HashMap<String,Integer> ingedients;
    ArrayList<String> ingredientsToShow;
    RecyclerView rvList;
    Button btnEmail;
    FirebaseAuth mAuth;
    FirebaseFunctionApi api;
    TextView txtDate;
    Spinner spnDays;
    List<String> days;
    int currentDay;
    LocalDateTime currentDate = new LocalDateTime();


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

        days = new ArrayList<String>();
        days.add("Lunes");
        days.add("Martes");
        days.add("Miércoles");
        days.add("Jueves");
        days.add("Viernes");
        days.add("Sábado");
        days.add("Domingo");
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

        ingredientsToShow = new ArrayList<String>();
        mAuth = FirebaseAuth.getInstance();
        rvList = (RecyclerView) view.findViewById(R.id.shopping_list_rv);
        btnEmail = (Button) view.findViewById(R.id.shopping_list_btn_mail);
        txtDate = (TextView) view.findViewById(R.id.txt_shopping_date_text);
        spnDays = (Spinner) view.findViewById(R.id.spn_shopping_list);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, days);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnDays.setAdapter(dataAdapter);
        spnDays.setOnItemSelectedListener(this);

        //setteo el spinner al dia actual
        currentDay = currentDate.getDayOfWeek();
        spnDays.setSelection(currentDay);


        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ingredientsToShow.size() == 0){
                    Toast.makeText(getContext(),"No hay ingredientes disponibles",Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseUser user = mAuth.getCurrentUser();
                    api = ServiceFactory.createRetrofitService(FirebaseFunctionApi.class, FirebaseFunctionApi.SERVICE_ENDPOINT);
                    api.sendList(new ShoppingListParams(user.getEmail(),ingredientsToShow)).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ResponseBody>() {
                                @Override
                                public final void onCompleted() {
                                }

                                @Override
                                public final void onError(Throwable e) {
                                    Toast.makeText(getContext(),"Se ha producido un error",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public final void onNext(ResponseBody rb){
                                    Toast.makeText(getContext(),"La lista ha sido enviada",Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = (TextView) parent.getChildAt(0);

        txt.setTextColor(Color.WHITE);

        int diff;
        LocalDateTime date = new LocalDateTime(currentDate);
        if(position + 1 > currentDay){
            diff = position + 1 - currentDay;
        }else{
            diff = Math.abs(7-currentDay) + position + 1;
        }
        date = date.plusDays(diff);
        String month = DateTimeFormat.forPattern("MMMM").withLocale(new Locale("es", "ES")).print(date);
        txtDate.setText(DATE_TEXT + date.getDayOfMonth() + " " + month);
        getList(diff);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void getList(final int diff){
        ingedients = new HashMap<>();
        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getCurrentUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                AgeHelper ageHelper = new AgeHelper();
                int planWeekStartDay = ageHelper.getPlanWeekStartDay(user.bebe.fechaDeNacimiento);

                if (planWeekStartDay + diff < 180)
                    planWeekStartDay += diff;

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
        ingredientsToShow.clear();
        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getIngredientsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator< HashMap<String,IngredienteListaSemanal>> t = new GenericTypeIndicator< HashMap<String,IngredienteListaSemanal>>() {};
                HashMap<String,IngredienteListaSemanal> ingredienteListaSemanalHm = dataSnapshot.getValue(t);

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
