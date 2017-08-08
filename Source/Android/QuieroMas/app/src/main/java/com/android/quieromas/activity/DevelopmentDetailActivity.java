package com.android.quieromas.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.TextKeyListener;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.quieromas.R;
import com.android.quieromas.adapter.DevelopmentRecyclerViewAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.DevelopmentItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DevelopmentDetailActivity extends BaseActivity {

    private int month;
    RecyclerView recyclerView;
    HashMap<String,DevelopmentItem> developmentItemsHm;
    ArrayList<DevelopmentItem> developmentItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            month = extras.getInt("month");
            setTitle(month + " meses");
        }

        recyclerView = (RecyclerView) findViewById(R.id.development_list);

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getDevelopmentReferenceByMonth(month).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,DevelopmentItem> >t = new GenericTypeIndicator<HashMap<String,DevelopmentItem>>() {};
                developmentItemsHm = dataSnapshot.getValue(t);
                addDevelopmentItems();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        android.support.v7.app.ActionBar bar = this.getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.blue)));

        Window window =  this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));
        }
//        }else{
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            statusBar.setBackgroundColor(ContextCompat.getColor(this,R.color.blue));
//            statusBar.setVisibility(View.VISIBLE);
//        }

    }

    void addDevelopmentItems(){
        developmentItems = new ArrayList<DevelopmentItem>();
        for (Map.Entry<String, DevelopmentItem> entry : developmentItemsHm.entrySet()) {
            String key = entry.getKey();
            DevelopmentItem value = entry.getValue();
            value.name = key;
            value.semana = Integer.parseInt(key.split(" ")[1]);
            developmentItems.add(value);
        }

        Collections.sort(developmentItems, new ComparatorSemana());

        recyclerView.setAdapter(new DevelopmentRecyclerViewAdapter(developmentItems));

    }

    @Override
    public void onStop() {
        super.onStop();
        android.support.v7.app.ActionBar bar = this.getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.orangePrimary)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =  this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(this,R.color.orangePrimary));
        }

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

    class ComparatorSemana implements Comparator<DevelopmentItem> {
        @Override
        public int compare(DevelopmentItem a, DevelopmentItem b) {
            return a.getSemana() < b.getSemana() ? -1 : a.getSemana() == b.getSemana() ? 0 : 1;
        }
    }



}
