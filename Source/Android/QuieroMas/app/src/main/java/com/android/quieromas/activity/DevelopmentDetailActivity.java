package com.android.quieromas.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.android.quieromas.R;

public class DevelopmentDetailActivity extends BaseActivity {

    private int month;

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

        android.support.v7.app.ActionBar bar = this.getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.blue)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =  this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        }

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


}
