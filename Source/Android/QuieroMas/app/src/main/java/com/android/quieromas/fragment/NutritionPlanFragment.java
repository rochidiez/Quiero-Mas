package com.android.quieromas.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;
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
    TextView txtBabyName;
    TextView txtPlanStage;
    FirebaseDatabaseHelper firebaseDatabaseHelper;
    User user;



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

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

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
        txtBabyName.setText(user.bebe.nombre);
    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(0), "Lun");
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(1), "Mar");
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(2), "Mie");
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(3), "Jue");
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(4), "Vie");
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(5), "Sab");
        adapter.addFragment(new NutritionPlanRecipesFragment().newInstance(6), "Dom");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onFragmentInteraction(Uri uri){
        Log.w("", "Hello");
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
