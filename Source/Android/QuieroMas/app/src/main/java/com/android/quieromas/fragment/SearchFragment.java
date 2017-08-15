package com.android.quieromas.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.receta.IngredienteListaSemanal;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.view.EmptyRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends BaseRecipeFragment implements AdapterView.OnItemSelectedListener {

    private static final String PROMPT = "Selecciona un ingrediente";
    FirebaseDatabaseHelper firebaseDatabaseHelper;
    ArrayList<Receta> allRecipes = new ArrayList<>();
    Button btnSearh;
    EditText etxtRecipeName;
    Spinner spnIngredients;
    List<String> ingredients;
    HashMap<String,String> hmIngredients;
    String selectedIngredient;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Todas las Recetas");
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

        spnIngredients = (Spinner) view.findViewById(R.id.spn_search);
        spnIngredients.setOnItemSelectedListener(this);


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
                    recipes.add(receta);
                }
                addRecipes();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseDatabaseHelper.getIngredientsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator< HashMap<String,IngredienteListaSemanal>> t = new GenericTypeIndicator< HashMap<String,IngredienteListaSemanal>>() {};
                HashMap<String,IngredienteListaSemanal> todosLosIngredientes = dataSnapshot.getValue(t);
                ingredients = new ArrayList<String>();
                hmIngredients = new HashMap<String, String>();

                ingredients.add(PROMPT);
                for (Map.Entry<String, IngredienteListaSemanal> ingredient : todosLosIngredientes.entrySet()) {
                    ingredients.add(ingredient.getValue().getNombre());
                    hmIngredients.put(ingredient.getValue().getNombre(),ingredient.getKey());
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ingredients);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnIngredients.setAdapter(dataAdapter);

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
                if(text.equals("")){
                    filteredRecipes = allRecipes;
                }else if(allRecipes.size() != 0){
                    for (Receta receta : allRecipes) {
                        if (receta.titulo.toLowerCase().contains(text) || text == "") {
                            if(selectedIngredient == null || receta.getIngredientesLista().containsKey(selectedIngredient)){
                                filteredRecipes.add(receta);
                            }
                        }
                    }
                }

                if(adapter != null){
                    adapter.updateValues(filteredRecipes);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = (TextView) parent.getChildAt(0);

        txt.setTextColor(Color.WHITE);

        if(txt.getText().toString() != PROMPT){
            selectedIngredient = hmIngredients.get(txt.getText().toString());
        }else{
            selectedIngredient = null;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
