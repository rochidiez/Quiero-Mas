package com.android.quieromas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.quieromas.R;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.nutricion.NutricionItem;
import com.android.quieromas.model.planDeNutricion.DiaPlanNutricion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AbcActivity extends BaseActivity {

    FirebaseDatabaseHelper firebaseDatabaseHelper;
    HashMap<String,ArrayList<NutricionItem>> data;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("El ABC de la nutrición");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_abc);

        linearLayout  = (LinearLayout) findViewById(R.id.abc_linear_layout);
         lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
         lp.setMargins(24,16,24,16);

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getNutritionMonthsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,ArrayList<NutricionItem>>> t = new  GenericTypeIndicator<HashMap<String,ArrayList<NutricionItem>>>() {};
                data = dataSnapshot.getValue(t);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void updateUI(){
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String,ArrayList<NutricionItem>> entry : data.entrySet()) {
            list.add(entry.getKey());
        }
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                int a = Integer.parseInt(s.split(" ")[0]);
                int b = Integer.parseInt(t1.split(" ")[0]);
                return a < b ? -1 : a == b ? 0 : 1;
            }
        });
        for(int i = 0; i < list.size(); i++){
            linearLayout.addView(createMonthButton(list.get(i)));
        }

    }

    Button createMonthButton(final String title){
        Button button = new Button(getApplicationContext());
        button.setText(title);
        button.generateViewId();
        button.setLayoutParams(lp);
        button.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_border_orange));
        button.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.orangePrimary));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AbcDetailActivity.class);
                intent.putExtra("month", title);
                startActivity(intent);
            }
        });

        return button;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
