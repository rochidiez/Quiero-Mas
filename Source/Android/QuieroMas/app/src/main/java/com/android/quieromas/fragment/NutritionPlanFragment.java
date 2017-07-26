package com.android.quieromas.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.helper.AgeHelper;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NutritionPlanFragment extends BaseFragment implements BaseFragment.OnFragmentInteractionListener {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout previousWeek;
    private LinearLayout nextWeek;
    ViewPagerAdapter adapter;
    TextView txtBabyName;
    TextView txtPlanStage;
    Button btnShoppingList;

    FirebaseDatabaseHelper firebaseDatabaseHelper;
    User user;
    int planWeek = -1;



    public NutritionPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Plan de nutrición");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nutrition_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        txtBabyName = (TextView) view.findViewById(R.id.txt_plan_baby_name);
        txtPlanStage = (TextView) view.findViewById(R.id.txt_plan_stage);
        previousWeek = (LinearLayout) view.findViewById(R.id.nutrition_plan_previous_week);
        nextWeek = (LinearLayout) view.findViewById(R.id.nutrition_plan_next_week);
        btnShoppingList = (Button) view.findViewById(R.id.btn_plan_shopping_list);


        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_content, new ShoppingListFragment(),"shopping_list");
                ft.addToBackStack("shopping_list");
                ft.commit();
            }
        });

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
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


    private void updateUI(){


        AgeHelper ageHelper = new AgeHelper();
        planWeek = ageHelper.getPlanWeek(user.bebe.fechaDeNacimiento);
        int totalWeek = ageHelper.getTotalWeeks(user.bebe.fechaDeNacimiento);
        int weekStartDay = ageHelper.getPlanWeekStartDay(user.bebe.fechaDeNacimiento);

        txtBabyName.setText(user.bebe.nombre);

        int months = (int) Math.floor(totalWeek / 4);
        int weeks = totalWeek % 4;
        String weekText = " semana";
        if(weeks > 1){
            weekText += "s";
        }
        txtPlanStage.setText(months + " meses, " + weeks + weekText);

        previousWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planWeek--;
                setupViewPager(viewPager, planWeek);
            }
        });

        nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planWeek++;
                setupViewPager(viewPager, planWeek);
            }
        });


        setupViewPager(viewPager, weekStartDay);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager, int day) {


        adapter = new ViewPagerAdapter(getFragmentManager());

        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(day), "Lun");
        day++;
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(day), "Mar");
        day++;
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(day), "Mie");
        day++;
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(day), "Jue");
        day++;
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(day), "Vie");
        day++;
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(day), "Sab");
        day++;
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(day), "Dom");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onFragmentInteraction(Uri uri){
        Log.w("", "Hello");
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<NutritionPlanRecipesFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public NutritionPlanRecipesFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(NutritionPlanRecipesFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
