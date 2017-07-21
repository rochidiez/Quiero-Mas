package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.quieromas.view.EmptyRecyclerView;
import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.activity.RecipeActivity;
import com.android.quieromas.adapter.MyFavoriteRecipesRecyclerViewAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.listener.ClickListener;
import com.android.quieromas.listener.RecyclerTouchListener;
import com.android.quieromas.model.receta.Receta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoriteRecipesFragment extends BaseRecipeFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public FavoriteRecipesFragment() {
    }

    public static FavoriteRecipesFragment newInstance(int columnCount) {
        FavoriteRecipesFragment fragment = new FavoriteRecipesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Recetas favoritas");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_favoriterecipes_list, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Context context = view.getContext();
        recyclerView = (EmptyRecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.setAdapter(new MyFavoriteRecipesRecyclerViewAdapter(recipes, mListener));
        recyclerView.setEmptyView(view.findViewById(R.id.empty_view));

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getFavoriteRecipesReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                recipesNames = dataSnapshot.getValue(t);
                if(recipesNames != null && recipesNames.size() > 0){
                    firebaseDatabaseHelper.getRecipesByNameReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<HashMap<String,Receta>> t = new GenericTypeIndicator<HashMap<String,Receta>>() {};
                            HashMap<String,Receta> hm = dataSnapshot.getValue(t);
                            for(int i = 0; i < recipesNames.size(); i++){
                                if(hm.containsKey(recipesNames.get(i))){
                                    Receta receta = hm.get(recipesNames.get(i));
                                    receta.titulo = recipesNames.get(i);
                                    receta.isFavorite = true;
                                    recipes.add(receta);
                                }
                            }
                            addRecipes();
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

}
