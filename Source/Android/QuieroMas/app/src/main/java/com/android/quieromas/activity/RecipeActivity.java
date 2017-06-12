package com.android.quieromas.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.quieromas.R;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        //Initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        //Show "back" button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnWatch = (Button) findViewById(R.id.btn_recipe_watch);
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"VIDEO", Toast.LENGTH_LONG).show();
            }
        });
    }
}
