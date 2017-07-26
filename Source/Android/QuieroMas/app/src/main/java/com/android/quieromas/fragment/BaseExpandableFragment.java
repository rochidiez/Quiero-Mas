package com.android.quieromas.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

import com.android.quieromas.adapter.QuieroMasExpandableListAdapter;
import com.android.quieromas.model.ExpandableListGroup;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lucas on 24/7/17.
 */

public class BaseExpandableFragment extends BaseFragment {

    ArrayList<ExpandableListGroup> groups;
    ExpandableListView expandableListView;


    public BaseExpandableFragment() {
        // Required empty public constructor
    }


    public static BaseExpandableFragment newInstance() {
        BaseExpandableFragment fragment = new BaseExpandableFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groups = new ArrayList<>();
    }

    public int getPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    public void updateUI(){

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        expandableListView.setAdapter(new QuieroMasExpandableListAdapter(this.getActivity(),groups));

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(width - getPixelFromDips(35), width - getPixelFromDips(5));
        } else {
            expandableListView.setIndicatorBoundsRelative(width - getPixelFromDips(35), width - getPixelFromDips(5));
        }

    }


}
