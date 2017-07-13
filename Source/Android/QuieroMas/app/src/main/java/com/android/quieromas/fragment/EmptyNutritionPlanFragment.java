package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;


public class EmptyNutritionPlanFragment extends BaseFragment {



    public EmptyNutritionPlanFragment() {
        // Required empty public constructor
    }

    public static EmptyNutritionPlanFragment newInstance() {
        EmptyNutritionPlanFragment fragment = new EmptyNutritionPlanFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Plan de nutrición");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_nutrition_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnEditProfile = (Button) view.findViewById(R.id.btn_empty_nutrition_plan_edit_profile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_content, new ProfileFragment(),"profile");
                ft.addToBackStack("profile");
                ft.commit();
            }
        });
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

}
