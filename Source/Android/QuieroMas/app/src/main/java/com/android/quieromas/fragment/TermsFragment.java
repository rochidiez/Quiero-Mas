package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TermsFragment extends BaseFragment {

    FirebaseDatabaseHelper firebaseDatabaseHelper;


    public TermsFragment() {
        // Required empty public constructor
    }


    public static TermsFragment newInstance() {
        TermsFragment fragment = new TermsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Términos y Condiciones");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_terms, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView terms = (TextView) view.findViewById(R.id.txt_terms_text);

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getTermsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String termsHtml = dataSnapshot.getValue(String.class);
                terms.setText(Html.fromHtml(termsHtml));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
