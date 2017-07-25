package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.AbcActivity;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.adapter.QuieroMasExpandableListAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.ExpandableListGroup;
import com.android.quieromas.model.lactancia.ItemLactancia;
import com.android.quieromas.model.nutricion.Nutricion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class AbcFragment extends BaseExpandableFragment {

    Nutricion data;
    TextView txtTitle;
    TextView txtSubtitle;
    RelativeLayout rlTitle;

    public AbcFragment() {
        // Required empty public constructor
    }

    public static AbcFragment newInstance(String param1, String param2) {
        AbcFragment fragment = new AbcFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("El ABC de la nutrición");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_abc, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        expandableListView = (ExpandableListView) view.findViewById(R.id.abc_exp_listView);


        //sets header view to expandableListView so it scrolls together
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View header= inflater.inflate(R.layout.abc_header, null, false);
        expandableListView.addHeaderView(header,null,false);

        txtTitle = (TextView) header.findViewById(R.id.txt_abc_title);
        txtSubtitle = (TextView) header.findViewById(R.id.txt_abc_subtitle);
        rlTitle = (RelativeLayout) header.findViewById(R.id.rl_abc_months);

        rlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AbcActivity.class);
                startActivity(intent);
            }
        });


        FirebaseDatabaseHelper firebaseDatabaseHelper =  new FirebaseDatabaseHelper();

        firebaseDatabaseHelper.getNutritionReference().child("Título").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String titulo = dataSnapshot.getValue(String.class);

                txtSubtitle.setText(Html.fromHtml(titulo));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        firebaseDatabaseHelper.getNutritionReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = dataSnapshot.getValue(Nutricion.class);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateUI(){


        for (Map.Entry<String, String> entry : data.getSecciones().entrySet()) {
            ExpandableListGroup group = new ExpandableListGroup(entry.getKey());
            group.children.add(entry.getValue());
            groups.add(group);
        }

        super.updateUI();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
