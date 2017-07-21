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
                    recipes.add(receta);
                }
                addRecipes();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
