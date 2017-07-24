package com.android.quieromas.fragment;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.android.quieromas.model.ExpandableListGroup;

import java.util.ArrayList;

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


}
