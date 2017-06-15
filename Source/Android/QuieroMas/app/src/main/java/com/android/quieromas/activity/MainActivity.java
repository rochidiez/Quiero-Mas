package com.android.quieromas.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.quieromas.R;
import com.android.quieromas.fragment.AbcFragment;
import com.android.quieromas.fragment.AboutUsFragment;
import com.android.quieromas.fragment.DevelopmentFragment;
import com.android.quieromas.fragment.FavoriteRecipesFragment;
import com.android.quieromas.fragment.HomeFragment;
import com.android.quieromas.fragment.LactationFragment;
import com.android.quieromas.fragment.NutritionPlanFragment;
import com.android.quieromas.fragment.ProfileEditFragment;
import com.android.quieromas.fragment.ProfileFragment;
import com.android.quieromas.fragment.ProfileViewFragment;
import com.android.quieromas.fragment.TermsFragment;
import com.android.quieromas.model.DummyContent;

public class MainActivity extends AuthActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener, NutritionPlanFragment.OnFragmentInteractionListener,
        LactationFragment.OnFragmentInteractionListener, FavoriteRecipesFragment.OnListFragmentInteractionListener,
        AbcFragment.OnFragmentInteractionListener, AboutUsFragment.OnFragmentInteractionListener, TermsFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener, ProfileEditFragment.OnFragmentInteractionListener, ProfileViewFragment.OnFragmentInteractionListener,
        DevelopmentFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";

    Fragment fragment = null;
    Class fragmentClass;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();

        View headerLayout = navigationView.getHeaderView(0);
        Button btnProfile = (Button) headerLayout.findViewById(R.id.btn_drawer_profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentClass = ProfileFragment.class;

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //setStatusBarTranslucent(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;
//        } else if (id == R.id.nav_recipes) {
//
        } else if (id == R.id.nav_nutrition_plan) {
            fragmentClass = NutritionPlanFragment.class;
//
        } else if (id == R.id.nav_education) {
            fragmentClass = DevelopmentFragment.class;
//
//        } else if (id == R.id.nav_nutrition) {
//
        } else if (id == R.id.nav_lactancy) {
            fragmentClass = LactationFragment.class;
//
//        } else if (id == R.id.nav_shopping_list) {
//
        } else if (id == R.id.nav_favorites) {
            fragmentClass = FavoriteRecipesFragment.class;
//
        } else if (id == R.id.nav_about) {
            fragmentClass = AboutUsFragment.class;
//        } else if (id == R.id.nav_share) {
//
        } else if (id == R.id.nav_tac) {
            fragmentClass = TermsFragment.class;
        } else {
            fragmentClass = HomeFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);

        item.setChecked(false);

        return true;
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        Log.w(TAG, "Hello");
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem di){
        Log.w(TAG, "Hello");
    }
}
