package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.adapter.QuieroMasExpandableListAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.ExpandableListGroup;
import com.android.quieromas.model.lactancia.Lactancia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AbcDetailFragment extends BaseExpandableFragment {

    TextView txtSubtitle;
    TextView txtTitle;
    HashMap<String,String> data;


    private String month;
    private static final String MONTH = "month";


    public AbcDetailFragment() {
        // Required empty public constructor
    }


    public static AbcDetailFragment newInstance(String month) {
        AbcDetailFragment fragment = new AbcDetailFragment();
        Bundle args = new Bundle();
        args.putString(MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            month = getArguments().getString(MONTH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abc_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        expandableListView = (ExpandableListView) view.findViewById(R.id.abc_detail_exp_listView);


        //sets header view to expandableListView so it scrolls together
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View header= inflater.inflate(R.layout.lactation_header, null, false);
        expandableListView.addHeaderView(header,null,false);

        txtTitle = (TextView) header.findViewById(R.id.lactation_title);
        txtTitle.setText("LECHE MATERNA Y/O FÓRMULA LÁCTEA RECOMENDADA");


        FirebaseDatabaseHelper firebaseDatabaseHelper =  new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getNutritionMonthReference(month).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,String>> t = new GenericTypeIndicator<HashMap<String,String>>() {};
                data = dataSnapshot.getValue(t);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateUI(){
        for (Map.Entry<String,String> entry : data.entrySet()) {
            ExpandableListGroup group = new ExpandableListGroup(entry.getKey());
            group.children.add(entry.getValue());
            groups.add(group);
        }

        super.updateUI();
    }

}
