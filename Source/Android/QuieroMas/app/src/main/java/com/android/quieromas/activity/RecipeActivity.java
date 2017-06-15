package com.android.quieromas.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.quieromas.R;

public class RecipeActivity extends AuthActivity {

    private Button btnDessert;
    private Button btnNutritionalTip;
    private Button btnDevelopmentTip;
    private Button btnWatch;

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

        btnWatch = (Button) findViewById(R.id.btn_recipe_watch);
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"VIDEO", Toast.LENGTH_LONG).show();
            }
        });


        btnNutritionalTip = (Button) findViewById(R.id.recipe_button_nutritional_tip);
        btnNutritionalTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TipActivity.class);
                intent.putExtra("title","Tip Nutricional");
                intent.putExtra("drawable","light_bulb");
                intent.putExtra("text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
                startActivity(intent);
            }
        });

        btnDevelopmentTip = (Button) findViewById(R.id.recipe_button_development_tip);
        btnDevelopmentTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TipActivity.class);
                intent.putExtra("title","Tip de Desarrollo");
                intent.putExtra("drawable","light_bulb");
                intent.putExtra("text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
                startActivity(intent);
            }
        });

        btnDessert = (Button) findViewById(R.id.recipe_button_dessert);
        btnDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TipActivity.class);
                intent.putExtra("title","Banana Pisada");
                intent.putExtra("drawable","apple");
                intent.putExtra("text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
                startActivity(intent);
            }
        });


    }
}
