package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.quienesSomos.QuienesSomos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AboutUsFragment extends BaseFragment {

    FirebaseDatabaseHelper firebaseDatabaseHelper;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Sobre Quiero Más!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();

        final TextView about = (TextView) view.findViewById(R.id.txt_about_text);
        final TextView title = (TextView) view.findViewById(R.id.txt_about_title);

        firebaseDatabaseHelper.getAboutUsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QuienesSomos quienesSomos = dataSnapshot.getValue(QuienesSomos.class);
                about.setText(Html.fromHtml(quienesSomos.getTexto()));
                if(quienesSomos.getTitulo() != null){
                    title.setText(Html.fromHtml(quienesSomos.getTitulo()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
