package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.RecipeActivity;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.planDeNutricion.Comida;
import com.android.quieromas.model.planDeNutricion.DiaPlanNutricion;
import com.android.quieromas.model.receta.Receta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NutritionPlanRecipesFragment extends BaseFragment {

    private int dia;
    DiaPlanNutricion data;
    Receta almuerzo;
    Receta cena;
    View almuerzoView;
    View cenaView;
    FirebaseDatabaseHelper firebaseDatabaseHelper;


    private OnFragmentInteractionListener mListener;

    public NutritionPlanRecipesFragment() {
        // Required empty public constructor
    }

    public static NutritionPlanRecipesFragment newInstance(int dia) {
        NutritionPlanRecipesFragment fragment = new NutritionPlanRecipesFragment();
        Bundle args = new Bundle();
        args.putInt("DIA",dia);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dia = getArguments().getInt("DIA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nutrition_plan_recipes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        almuerzoView = inflater.inflate(R.layout.fragment_recipe_item, null);
        FrameLayout almuerzoContainer = (FrameLayout) view.findViewById(R.id.recipe_lunch_container);
        almuerzoContainer.addView(almuerzoView);

        cenaView = inflater.inflate(R.layout.fragment_recipe_item, null);
        FrameLayout dinnerContainer = (FrameLayout) view.findViewById(R.id.recipe_dinner_container);
        dinnerContainer.addView(cenaView);

        firebaseDatabaseHelper =  new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getPlanByAgeReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<DiaPlanNutricion>> t = new GenericTypeIndicator<ArrayList<DiaPlanNutricion>>() {};
                ArrayList<DiaPlanNutricion> dataArray = dataSnapshot.getValue(t);
                data = dataArray.get(dia);
               getReceta(true);
               getReceta(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateUI(boolean isAlmuerzo){
        View meal;
        String mealText;
        final Comida comida;
        final Receta receta;

        if(isAlmuerzo){
            meal = almuerzoView;
            mealText = "Para el almuerzo";
            receta = almuerzo;
            comida = data.getAlmuerzo();
        }else{
            meal = cenaView;
            mealText = "Para la cena";
            receta = cena;
            comida = data.getCena();
        }
        ImageView imgMeal = (ImageView) meal.findViewById(R.id.img_fav_background);
        TextView txtMeal = (TextView) meal.findViewById(R.id.txt_recipe_meal);
        TextView txtTitle = (TextView) meal.findViewById(R.id.txt_recipe_title);

        txtMeal.setText(mealText);
        txtTitle.setText(comida.getReceta());

        Picasso.with(getContext()).load(receta.getThumbnail())
                .fit()
                .into(imgMeal);

        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                intent.putExtra("RECIPE", comida.getReceta());
                intent.putExtra("DESSERT",comida.getPostre());
                intent.putExtra("DEVELOPMENT",data.getTipDesarrollo());
                startActivity(intent);
            }
        });
    }

    public void getReceta(final Boolean isAlmuerzo){
        Comida comida;
        if(isAlmuerzo){
            comida = data.getAlmuerzo();
        }else{
            comida = data.getCena();
        }
        firebaseDatabaseHelper.getRecipeReference(comida.getReceta()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(isAlmuerzo){
                    almuerzo = dataSnapshot.getValue(Receta.class);
                    updateUI(true);
                }else{
                    cena = dataSnapshot.getValue(Receta.class);
                    updateUI(false);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
