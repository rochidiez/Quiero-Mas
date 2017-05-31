package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.quieromas.R;
import com.android.quieromas.adapter.QuieroMasExpandableListAdapter;
import com.android.quieromas.model.ExpandableListGroup;

import java.util.ArrayList;


public class LactationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ExpandableListView expandableListView;
    ArrayList<ExpandableListGroup> groups;

    public LactationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
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
        expandableListView.setAdapter(new QuieroMasExpandableListAdapter(this.getActivity(),groups));
        //expandableListView.setGroupIndicator(null);

    }

    public void getData() {
//        for (int j = 0; j < 5; j++) {
//            ExpandableListGroup group = new ExpandableListGroup("Test " + j);
//            for (int i = 0; i < 5; i++) {
//                group.children.add("Sub Item" + i);
//            }
//            groups.add(group);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}


