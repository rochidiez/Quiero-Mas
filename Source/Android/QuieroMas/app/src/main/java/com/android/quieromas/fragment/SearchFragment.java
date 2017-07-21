package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.quieromas.R;
import com.android.quieromas.adapter.MyFavoriteRecipesRecyclerViewAdapter;
import com.android.quieromas.adapter.MyPlanRecipesRecyclerViewAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.view.EmptyRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends BaseRecipeFragment {

    FirebaseDatabaseHelper firebaseDatabaseHelper;
    ArrayList<Receta> allRecipes = new ArrayList<>();
    Button btnSearh;
    EditText etxtRecipeName;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (EmptyRecyclerView) view.findViewById(R.id.search_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getRecipesByNameReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,Receta>> t = new GenericTypeIndicator<HashMap<String,Receta>>() {};
                HashMap<String, Receta> recetasHm = dataSnapshot.getValue(t);
                for ( Map.Entry<String, Receta> entry : recetasHm.entrySet()) {

                    Receta receta = entry.getValue();
                    receta.titulo = entry.getKey();
                    allRecipes.add(receta);
                }
                recipes = allRecipes;
                addRecipes();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        etxtRecipeName = (EditText) view.findViewById(R.id.etxt_search_recipe);
        btnSearh = (Button) view.findViewById(R.id.btn_search_search);

        etxtRecipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etxtRecipeName.setText("");
            }
        });

        btnSearh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String text = etxtRecipeName.getText().toString();
                ArrayList<Receta> filteredRecipes = new ArrayList<>();
                if(recipes.size() != 0){
                    for (Receta receta : recipes) {
                        if (receta.titulo.contains(text)) {
                            filteredRecipes.add(receta);
                        }
                    }
                }
                if(text == ""){
                    filteredRecipes = allRecipes;
                }
                if(adapter != null){
                    adapter.updateValues(filteredRecipes);
                }
            }
        });

    }

}
