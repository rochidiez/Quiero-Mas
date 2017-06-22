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
import com.android.quieromas.model.ExpandableListGroup;

import java.util.ArrayList;


public class LactationFragment extends BaseFragment {

    ExpandableListView expandableListView;
    ArrayList<ExpandableListGroup> groups;
    TextView txtSubtitle;

    public LactationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groups = new ArrayList<>();
        getData();
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

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        expandableListView = (ExpandableListView) view.findViewById(R.id.lactation_exp_listView);
        expandableListView.setAdapter(new QuieroMasExpandableListAdapter(this.getActivity(),groups));
        expandableListView.setIndicatorBounds(width - getPixelFromDips(50), width - getPixelFromDips(10));


        //sets header view to expandableListView so it scrolls together
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View header= inflater.inflate(R.layout.lactation_header, null, false);
        expandableListView.addHeaderView(header,null,false);

        txtSubtitle = (TextView) header.findViewById(R.id.lactation_subtitle_text);
        txtSubtitle.setText(Html.fromHtml("Amamantar a tu bebe es un momento único e íntimo para ambos. Le estás ofreciendo el mejor alimento que podés brindarle.</p><p>Pero no es solamente una razón nutricional, sino que es la continuación de una profunda relación de amor que comenzó en el mismo momento de la concepción.</p>"));

    }

    public int getPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    public void getData() {
        for (int j = 0; j < 5; j++) {
            ExpandableListGroup group = new ExpandableListGroup("Por qué es mejor darle el pecho que la mamadera con una leche maternizada?");
            for (int i = 0; i < 1; i++) {
                group.children.add("Tu bebé cumplió 6 meses y ya puede comenzar con sus primeras comidas. Han madurado su función digestiva y renal...");
            }
            groups.add(group);
        }
    }
}


