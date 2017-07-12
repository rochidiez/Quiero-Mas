package com.android.quieromas.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.adapter.QuieroMasExpandableListAdapter;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.DevelopmentItem;
import com.android.quieromas.model.ExpandableListGroup;
import com.android.quieromas.model.lactancia.ItemLactancia;
import com.android.quieromas.model.lactancia.Lactancia;
import com.android.quieromas.model.receta.Receta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LactationFragment extends BaseFragment {

    ExpandableListView expandableListView;
    ArrayList<ExpandableListGroup> groups;
    TextView txtSubtitle;
    TextView txtTitle;
    Lactancia data;

    public LactationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groups = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Lactancia");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lactation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        expandableListView = (ExpandableListView) view.findViewById(R.id.lactation_exp_listView);


        //sets header view to expandableListView so it scrolls together
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View header= inflater.inflate(R.layout.lactation_header, null, false);
        expandableListView.addHeaderView(header,null,false);

        txtTitle = (TextView) header.findViewById(R.id.lactation_title);
        txtSubtitle = (TextView) header.findViewById(R.id.lactation_subtitle_text);


        FirebaseDatabaseHelper firebaseDatabaseHelper =  new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getLactationReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = dataSnapshot.getValue(Lactancia.class);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateUI(){

        if(data.getTitulo() != null){
            txtTitle.setText(data.getTitulo().getTitulo());
            txtSubtitle.setText(data.getTitulo().getTexto());
        }

        for(int i = 0; i < data.getTabla().size(); i++){
            ItemLactancia elem = data.getTabla().get(i);
            ExpandableListGroup group = new ExpandableListGroup(elem.getTitulo());
            group.children.add(elem.getHtml());
            groups.add(group);
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        expandableListView.setAdapter(new QuieroMasExpandableListAdapter(this.getActivity(),groups));

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(width - getPixelFromDips(50), width - getPixelFromDips(10));
        } else {
            expandableListView.setIndicatorBoundsRelative(width - getPixelFromDips(50), width - getPixelFromDips(10));
        }
    }

    public int getPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}


