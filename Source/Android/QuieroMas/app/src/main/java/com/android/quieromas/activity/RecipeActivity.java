package com.android.quieromas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.quieromas.R;
import com.android.quieromas.adapter.RecipeRecyclerViewAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.receta.Ingrediente;
import com.android.quieromas.model.receta.Postre;
import com.android.quieromas.model.receta.Puntaje;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.model.receta.RecipeStepElement;
import com.android.quieromas.model.user.Bebe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

public class RecipeActivity extends AuthActivity {

    private Button btnDessert;
    private Button btnNutritionalTip;
    private Button btnDevelopmentTip;
    private Button btnWatch;
    private Button btnReuse;
    private String recipeName;
    private Receta receta;
    private ImageView imgBackground;
    private String dessertName;
    private String developmentTip;
    private TextView txtVariants;
    private TextView txtName;
    private RecyclerView rvSteps;
    private RecyclerView rvIngredients;
    private RelativeLayout reuseLayout;
    private LinearLayout llRating;
    private ArrayList<Button> btnRatingArray;
    FirebaseDatabaseHelper firebaseDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        //Initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        //Show "back" button
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipeName = extras.getString("RECIPE");
            if(extras.containsKey("DESSERT")){
                dessertName = extras.getString("DESSERT");
            }
            if(extras.containsKey("DEVELOPMENT")){
                developmentTip = extras.getString("DEVELOPMENT");
            }
        }

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getRecipeReference(recipeName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                receta = dataSnapshot.getValue(Receta.class);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnDessert = (Button) findViewById(R.id.recipe_button_dessert);
        imgBackground = (ImageView) findViewById(R.id.img_recipe_image);
        btnWatch = (Button) findViewById(R.id.btn_recipe_watch);
        btnNutritionalTip = (Button) findViewById(R.id.recipe_button_nutritional_tip);
        btnDevelopmentTip = (Button) findViewById(R.id.recipe_button_development_tip);
        txtVariants = (TextView) findViewById(R.id.txt_recipe_variants);
        txtName = (TextView) findViewById(R.id.txt_recipe_name);
        rvSteps = (RecyclerView) findViewById(R.id.recipe_list_steps);
        rvIngredients = (RecyclerView) findViewById(R.id.recipe_list_ingredients);
        reuseLayout = (RelativeLayout) findViewById(R.id.recipe_layout_reuse);
        btnReuse = (Button) findViewById(R.id.recipe_button_reuse);
        llRating = (LinearLayout) findViewById(R.id.recipe_rating_container);

        btnRatingArray = new ArrayList<>();

        txtName.setText(recipeName);

        if(developmentTip == null){
            btnDevelopmentTip.setVisibility(View.GONE);
        }

        if(dessertName == null){
            btnDessert.setVisibility(View.GONE);
            reuseLayout.setVisibility(View.GONE);
        }else{

            btnReuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ConservationActivity.class);
                    startActivity(intent);
                }
            });

            firebaseDatabaseHelper =  new FirebaseDatabaseHelper();
            firebaseDatabaseHelper.getDessertReference(dessertName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final Postre dessert = dataSnapshot.getValue(Postre.class);
                    btnDessert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RecipeActivity.this, BasicRecipeActivity.class);
                            intent.putExtra("BASIC_RECIPE",dessertName);
                            intent.putExtra("IS_DESSERT",true);
                            getApplicationContext().startActivity(intent);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    private void startTipActivity(String title, String text, String drawable){
        Intent intent = new Intent(getApplicationContext(), TipActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("drawable",drawable);
        intent.putExtra("text", text);
        startActivity(intent);
    }

    private void updateUI(){

        int i = 0;
        final Puntaje puntaje = receta.getPuntaje();
        if (puntaje.datos.containsKey(mAuth.getCurrentUser().getUid())){
            i = puntaje.datos.get(mAuth.getCurrentUser().getUid());
        }
        int j = 1;
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dimensionInDp,dimensionInDp);
        params.setMargins(12,8,12,8);
        while(j <= 5){
            Button btn = new Button(this);
            btn.setLayoutParams(params);
            btn.setText(String.valueOf(j));
            // btn.setTextSize(getResources().getDimension(R.dimen.subsubtitle_text_size));
            if(i>0){
                btn.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                btn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_circle_orange));

                i--;
            }else{
                btn.setTextColor(ContextCompat.getColor(this, R.color.orangePrimary));
                btn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_circle_border));
            }

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    int score =  Integer.parseInt(button.getText().toString());
                    int index = score - 1;
                    for(int i = 0; i < btnRatingArray.size(); i++){
                        Button it = btnRatingArray.get(i);
                        if(i <= index){
                            it.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                            it.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_circle_orange));
                        }else{
                            it.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orangePrimary));
                            it.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_circle_border));
                        }
                    }
                    puntaje.datos.put(mAuth.getCurrentUser().getUid(),score);
                    firebaseDatabaseHelper.getScoreReference(recipeName).setValue(puntaje);
                    Toast.makeText(getApplicationContext(),"Su puntaje ha sido guardado.",Toast.LENGTH_LONG).show();
                }
            });

            llRating.addView(btn);
            btnRatingArray.add(btn);
            j++;
        }


        btnDevelopmentTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTipActivity("Tip de Desarrollo",developmentTip,"light_bulb");
            }
        });

        btnNutritionalTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTipActivity("Tip Nutricional",receta.getTip_nutricional(),"light_bulb");

            }
        });

        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("URL",receta.getVideo());
                startActivity(intent);
            }
        });

        Picasso.with(getApplicationContext()).load(receta.getThumbnail())
                .fit()
                .into(imgBackground);

        txtVariants.setText(receta.getVariante());

        //Set steps
        ArrayList<RecipeStepElement> stepsList = new ArrayList<>();
        for (ListIterator<String> iter = receta.getPasos().listIterator(); iter.hasNext(); ) {
            String element = iter.next();
            stepsList.add(new RecipeStepElement(element));
        }
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.setAdapter(new RecipeRecyclerViewAdapter(stepsList,true));

        //set ingredients
        ArrayList<RecipeStepElement> ingredientsList = new ArrayList<>();
        for (ListIterator<Ingrediente> iter = receta.getIngredientes().listIterator(); iter.hasNext(); ) {
            Ingrediente element = iter.next();
            ingredientsList.add(new RecipeStepElement(element.getNombre(),element.getNombre_basico()));
        }
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        rvIngredients.setAdapter(new RecipeRecyclerViewAdapter(ingredientsList,false));
    }
}
