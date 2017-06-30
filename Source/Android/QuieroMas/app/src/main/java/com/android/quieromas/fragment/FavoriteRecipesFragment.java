package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.quieromas.EmptyRecyclerView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteRecipesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    ArrayList<String> favoriteRecipesNames = new ArrayList<>();
    ArrayList<Receta> favoriteRecipes = new ArrayList<>();

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

        // Set the adapter
        if (view instanceof EmptyRecyclerView) {
            Context context = view.getContext();
            EmptyRecyclerView recyclerView = (EmptyRecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setEmptyView(view.findViewById(R.id.empty_view));
            recyclerView.setAdapter(new MyFavoriteRecipesRecyclerViewAdapter(favoriteRecipes, mListener));

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                    recyclerView, new ClickListener() {

                @Override
                public void onClick(View view, final int position) {
                    String name = favoriteRecipesNames.get(position);
                    Intent intent = new Intent(getActivity(), RecipeActivity.class);
                    intent.putExtra("RECIPE", name);
                    startActivity(intent);
                }


            }));
        }

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getFavoriteRecipesReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favoriteRecipesNames = dataSnapshot.getValue(ArrayList.class);
                addRecipes();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }


    void addRecipes(){}



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
        // TODO: Update argument type and name
        void onListFragmentInteraction(Receta item);
    }
}
