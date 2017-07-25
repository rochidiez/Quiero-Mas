package com.android.quieromas.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.quieromas.R;
import com.android.quieromas.fragment.AbcDetailFragment;
import com.android.quieromas.fragment.BaseFragment;

public class AbcDetailActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener{

    String month;
    private static final String TAG = "AbcDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_abc_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            month = extras.getString("month");
            setTitle(month);
        }

        AbcDetailFragment fragment = AbcDetailFragment.newInstance(month);
        getSupportFragmentManager().beginTransaction().add(R.id.abc_detail_fragment_container,fragment).commit();

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

    @Override
    public void onFragmentInteraction(Uri uri){
        Log.w(TAG, "Hello");
    }
}
