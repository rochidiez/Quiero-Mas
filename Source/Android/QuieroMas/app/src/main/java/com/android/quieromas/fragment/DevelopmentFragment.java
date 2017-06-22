package com.android.quieromas.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.quieromas.R;
import com.android.quieromas.activity.DevelopmentDetailActivity;
import com.android.quieromas.activity.MainActivity;

public class DevelopmentFragment extends BaseFragment {

    public DevelopmentFragment() {
        // Required empty public constructor
    }

    public static DevelopmentFragment newInstance() {
        DevelopmentFragment fragment = new DevelopmentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Desarrollo");
        android.support.v7.app.ActionBar bar = ((MainActivity) getActivity()).getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.blue)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =  ((MainActivity) getActivity()).getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.blue));
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        android.support.v7.app.ActionBar bar = ((MainActivity) getActivity()).getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.orangePrimary)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =  ((MainActivity) getActivity()).getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.orangePrimary));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.development_linear_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(24,12,24,12);
        for(int i = 6; i <= 12; i++){
            Button button = createMonthButton(i);
            linearLayout.addView(button, lp);
        }
    }

    Button createMonthButton(final int month){
        Button button = new Button(getContext());
        button.setText(month + " meses");
        button.generateViewId();
        button.setBackground(getResources().getDrawable(R.drawable.btn_development));
        button.setTextColor(getResources().getColor(R.color.colorPrimary));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DevelopmentDetailActivity.class);
                intent.putExtra("month", month);
                startActivity(intent);
            }
        });

        return button;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_development, container, false);
    }
}
