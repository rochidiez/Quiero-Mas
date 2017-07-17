package com.android.quieromas.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.fragment.AbcFragment;
import com.android.quieromas.fragment.AboutUsFragment;
import com.android.quieromas.fragment.BaseFragment;
import com.android.quieromas.fragment.DevelopmentFragment;
import com.android.quieromas.fragment.EmptyNutritionPlanFragment;
import com.android.quieromas.fragment.FavoriteRecipesFragment;
import com.android.quieromas.fragment.HomeFragment;
import com.android.quieromas.fragment.LactationFragment;
import com.android.quieromas.fragment.NutritionPlanFragment;
import com.android.quieromas.fragment.ProfileFragment;
import com.android.quieromas.fragment.ShoppingListFragment;
import com.android.quieromas.fragment.TermsFragment;
import com.android.quieromas.helper.AgeHelper;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.model.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AuthActivity
        implements NavigationView.OnNavigationItemSelectedListener, FavoriteRecipesFragment.OnListFragmentInteractionListener,
        BaseFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";

    Fragment fragment = null;
    Class fragmentClass;
    Class nutritionPlanFragment;
    DrawerLayout drawer;
    TextView txtName;
    User user;

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
        nutritionPlanFragment = EmptyNutritionPlanFragment.class;
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

        txtName = (TextView) headerLayout.findViewById(R.id.txt_drawer_name);

    }

    void userLoggedEvent(){
        super.userLoggedEvent();

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        //All recipes
        syncDatabaseForReference(firebaseDatabaseHelper.getRecipesReference());

        //All desserts
        syncDatabaseForReference(firebaseDatabaseHelper.getDessertsReference());

        //Current user
        syncDatabaseForReference(firebaseDatabaseHelper.getCurrentUserReference());

        //Lactation
        syncDatabaseForReference(firebaseDatabaseHelper.getLactationReference());

        firebaseDatabaseHelper.getCurrentUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateUI(){
        String names;
        names = user.datos.nombreCompleto.split(" ")[0];
        if(user.bebe != null && user.bebe.apodo != null){
            names = names + " y " + user.bebe.apodo;
        }else if(user.bebe != null && user.bebe.nombre != null){
            names = names + " y " + user.bebe.nombre;
        }
        txtName.setText(names);

        AgeHelper ageHelper = new AgeHelper();
        if(user.bebe != null){
            if(ageHelper.canAccessPlan(user.bebe.fechaDeNacimiento)){
                nutritionPlanFragment = NutritionPlanFragment.class;
            }else{
                nutritionPlanFragment = EmptyNutritionPlanFragment.class;
            }
        }else{
            nutritionPlanFragment = EmptyNutritionPlanFragment.class;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        super.onAuthStateChanged(firebaseAuth);

    }

    private void syncDatabaseForReference(DatabaseReference ref){
        ref.keepSynced(true);
        ref.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG,"Updated local datebase for: " + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            fragmentClass = nutritionPlanFragment;
        } else if (id == R.id.nav_education) {
            fragmentClass = DevelopmentFragment.class;

        } else if (id == R.id.nav_nutrition) {
            fragmentClass = AbcFragment.class;
        } else if (id == R.id.nav_lactancy) {
            fragmentClass = LactationFragment.class;

        } else if (id == R.id.nav_shopping_list) {
            fragmentClass = ShoppingListFragment.class;
        } else if (id == R.id.nav_favorites) {
            fragmentClass = FavoriteRecipesFragment.class;
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
    public void onListFragmentInteraction(Receta di){
        Log.w(TAG, "Hello");
    }
}
