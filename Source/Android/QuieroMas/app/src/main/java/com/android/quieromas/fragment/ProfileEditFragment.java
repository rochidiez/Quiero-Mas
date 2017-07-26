package com.android.quieromas.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.activity.ChangePasswordActivity;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.user.Bebe;
import com.android.quieromas.model.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileEditFragment extends BaseFragment {

    FirebaseDatabaseHelper firebaseDatabaseHelper;
    FormEditText txtName;
    FormEditText txtEmail;
    FormEditText txtBirthdate;
    FormEditText txtBabyName;
    FormEditText txtBabyNickname;
    FormEditText txtBabyBirthdate;
    Button btnSaveChanges;


    User user;
    String name;
    String email;
    String birthdate;
    String babyName;
    String babyNickname;
    String babyBirthdate;
    boolean hasBaby;
    Date babyDate;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String formattedBirthdate;
    private String formattedBabyBirthdate;

    public ProfileEditFragment() {
        // Required empty public constructor
    }

    public static ProfileEditFragment newInstance(String param1, String param2) {
        ProfileEditFragment fragment = new ProfileEditFragment();
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
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hasBaby = true;
        babyDate = new Date();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Button btnChangePassword = (Button) view.findViewById(R.id.btn_profile_edit_change_password);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        txtName = (FormEditText) view.findViewById(R.id.etxt_edit_profile_name);
        txtEmail = (FormEditText) view.findViewById(R.id.etxt_edit_profile_email);
        txtBirthdate = (FormEditText) view.findViewById(R.id.etxt_edit_profile_birthdate);
        txtBabyName = (FormEditText) view.findViewById(R.id.etxt_edit_profile_baby_name);
        txtBabyNickname  = (FormEditText) view.findViewById(R.id.etxt_edit_profile_baby_nickname);
        txtBabyBirthdate = (FormEditText) view.findViewById(R.id.etxt_edit_profile_baby_birthdate);
        btnSaveChanges = (Button) view.findViewById(R.id.btn_edit_profile_save_changes);

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getCurrentUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                name = user.datos.nombreCompleto;
                email = user.datos.email;
                birthdate = user.datos.fechaDeNacimiento;
                if(user.bebe != null){
                    hasBaby = true;
                    babyName = user.bebe.nombre;
                    babyNickname = user.bebe.apodo;
                    babyBirthdate = user.bebe.fechaDeNacimiento;
                }else{
                    hasBaby = false;
                    babyName = "";
                    babyNickname = "";
                    babyBirthdate = "";
                }
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidForm()){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MONTH, -6);
                    hasBaby = hasBaby || cal.after(babyDate);
                    user.datos.nombreCompleto = txtName.getText().toString();
                    user.datos.email = txtEmail.getText().toString();
                    user.datos.fechaDeNacimiento = txtBirthdate.getText().toString();
                    if(hasBaby){
                        Bebe bebe = new Bebe(txtBabyName.getText().toString(),txtBabyNickname.getText().toString(),
                                txtBirthdate.getText().toString());
                        user.bebe = bebe;
                    }
                    firebaseDatabaseHelper.getCurrentUserReference().setValue(user);

                    Toast.makeText(getContext(),"Los cambios han sido guardados con éxito",Toast.LENGTH_LONG).show();
                }
            }
        });

        txtBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(v.getContext(),R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            formattedBirthdate = dateFormatter.format(newDate.getTime());
                            txtBirthdate.setText(formattedBirthdate);
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                }
            }
        });

        txtBabyBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(v.getContext(),R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            babyDate = newDate.getTime();
                            formattedBirthdate = dateFormatter.format(newDate.getTime());
                            txtBabyBirthdate.setText(formattedBirthdate);
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                }
            }
        });

    }

    public void updateUI(){
        txtName.setText(name);
        txtEmail.setText(email);
        txtBirthdate.setText(birthdate);
        if(hasBaby){
            txtBabyName.setText(babyName);
            txtBabyNickname.setText(babyNickname);
            txtBabyBirthdate.setText(babyBirthdate);
        }
    }

    private boolean isValidForm(){
        boolean isValid = true;
        FormEditText[] fields;
        FormEditText[] allFields = { txtEmail, txtName, txtBirthdate, txtBabyName, txtBabyNickname, txtBabyBirthdate };
        FormEditText[] userFields = { txtEmail, txtName, txtBirthdate};
        //check if user has modified any of the baby fields
        if(hasBaby || txtBabyName.getText().toString() != ""
                || txtBabyBirthdate.getText().toString() != "" || txtBabyNickname.getText().toString() != ""){
            fields = allFields;
        }else{
            fields = userFields;
        }

        for (FormEditText field: fields) {
            isValid = field.testValidity() && isValid;
        }
        return isValid;
    }

}
