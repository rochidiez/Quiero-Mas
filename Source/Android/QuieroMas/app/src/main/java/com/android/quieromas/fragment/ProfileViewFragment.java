package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.ChangePasswordActivity;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.model.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ProfileViewFragment extends BaseFragment {

    FirebaseDatabaseHelper firebaseDatabaseHelper;
    TextView txtName;
    TextView txtEmail;
    TextView txtBirthdate;
    TextView txtBabyName;
    TextView txtBabyNickname;
    TextView txtBabyBirthdate;
    User user;

    public ProfileViewFragment() {
        // Required empty public constructor
    }


    public static ProfileViewFragment newInstance() {
        ProfileViewFragment fragment = new ProfileViewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnChangePassword = (Button) view.findViewById(R.id.btn_profile_view_change_password);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        Button btnEditProfile = (Button) view.findViewById(R.id.btn_profile_view_edit);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.profile_edit_container, new ProfileEditFragment(),"edit_profile");
                ft.addToBackStack("edit_profile");
                ft.commit();
            }
        });

        txtName = (TextView) view.findViewById(R.id.txt_profile_view_name);
        txtEmail = (TextView) view.findViewById(R.id.txt_profile_view_email);
        txtBirthdate = (TextView) view.findViewById(R.id.txt_profile_view_birthdate);
        txtBabyName = (TextView) view.findViewById(R.id.txt_profile_view_baby_name);
        txtBabyNickname  = (TextView) view.findViewById(R.id.txt_profile_view_baby_nickname);
        txtBabyBirthdate = (TextView) view.findViewById(R.id.txt_profile_view_baby_birthdate);

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getCurrentUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateUI(){
        txtName.setText(user.getDatos().getNombreCompleto());
        txtEmail.setText(user.getDatos().getEmail());
        txtBirthdate.setText(user.getDatos().getFechaDeNacimiento());

        if(user.getBebe() != null){
            txtBabyName.setText(user.getBebe().getNombre());
            txtBabyNickname.setText(user.getBebe().getApodo());
            txtBabyBirthdate.setText(user.getBebe().getFechaDeNacimiento());
        }
    }
}
