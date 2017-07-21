package com.android.quieromas.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.quieromas.activity.RecipeActivity;
import com.android.quieromas.adapter.MyFavoriteRecipesRecyclerViewAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.listener.ClickListener;
import com.android.quieromas.listener.RecyclerTouchListener;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.view.EmptyRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lucas on 21/7/17.
 */

public class BaseRecipeFragment extends Fragment {

    OnListFragmentInteractionListener mListener;
    ArrayList<Receta> recipes = new ArrayList<>();
    FirebaseDatabaseHelper firebaseDatabaseHelper;
    EmptyRecyclerView recyclerView;
    MyFavoriteRecipesRecyclerViewAdapter adapter;


    void addRecipes(){
        adapter = new MyFavoriteRecipesRecyclerViewAdapter(recipes, mListener);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                String name = recipes.get(position).getTitulo();
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                intent.putExtra("RECIPE", name);
                startActivity(intent);
            }


        }));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Receta item);
    }
}
