package com.android.quieromas.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.adapter.RecipeRecyclerViewAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.model.receta.RecetaBasica;
import com.android.quieromas.model.receta.RecipeStepElement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.ListIterator;

public class BasicRecipeActivity extends BaseActivity {

    TextView txtName;
    ImageView imgBackground;
    ImageView imgIcon;
    RecyclerView rvIngredients;
    RecyclerView rvSteps;
    Button btnWatch;
    Button btnClose;
    String recipeName;
    RecetaBasica recetaBasica;
    boolean isDessert = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_recipe);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipeName = extras.getString("BASIC_RECIPE");
            if(extras.containsKey("IS_DESSERT")){
                isDessert = extras.getBoolean("IS_DESSERT");
            }
        }

        txtName = (TextView) findViewById(R.id.txt_basic_recipe_name);
        txtName.setText(recipeName);
        imgBackground = (ImageView) findViewById(R.id.img_basic_recipe_image);
        imgIcon = (ImageView) findViewById(R.id.img_basic_recipe_icon);
        btnWatch = (Button) findViewById(R.id.btn_basic_recipe_watch);
        btnClose = (Button) findViewById(R.id.btn_basic_recipe_close);
        rvIngredients = (RecyclerView) findViewById(R.id.basic_recipe_list_ingredients);
        rvSteps = (RecyclerView) findViewById(R.id.basic_recipe_list_steps);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        DatabaseReference databaseReference;
        if(isDessert){
            databaseReference = firebaseDatabaseHelper.getDessertReference(recipeName);
            imgIcon.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.apple));
        }else{
            databaseReference = firebaseDatabaseHelper.getBasicRecipeReference(recipeName);
        }
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recetaBasica = dataSnapshot.getValue(RecetaBasica.class);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateUI(){

        Picasso.with(getApplicationContext()).load(recetaBasica.getThumbnail())
                .fit()
                .into(imgBackground);

        //Set steps
        ArrayList<RecipeStepElement> stepsList = new ArrayList<>();
        for (ListIterator<String> iter = recetaBasica.getIngredientes().listIterator(); iter.hasNext(); ) {
            String element = iter.next();
            stepsList.add(new RecipeStepElement(element));
        }
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        rvIngredients.setAdapter(new RecipeRecyclerViewAdapter(stepsList,false));

        //Set steps
        ArrayList<RecipeStepElement> ingList = new ArrayList<>();
        for (ListIterator<String> iter = recetaBasica.getPasos().listIterator(); iter.hasNext(); ) {
            String element = iter.next();
            ingList.add(new RecipeStepElement(element));
        }
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.setAdapter(new RecipeRecyclerViewAdapter(ingList,true));

        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("URL",recetaBasica.getVideo());
                startActivity(intent);
            }
        });
    }
}
