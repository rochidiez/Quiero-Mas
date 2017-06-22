package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbcPerMonthFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AbcPerMonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbcPerMonthFragment extends BaseFragment {

    public AbcPerMonthFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abc_per_month, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
