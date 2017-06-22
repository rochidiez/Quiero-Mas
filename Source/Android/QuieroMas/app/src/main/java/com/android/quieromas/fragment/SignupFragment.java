package com.android.quieromas.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.activity.CheckIfBornedActivity;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.validator.RepeatPasswordValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupFragment extends BaseFragment {

    private Button btnCreateAccount;
    private FormEditText email;
    private FormEditText password;
    private FormEditText passwordRepeat;
    private FormEditText birthdate;
    private FormEditText name;
    private FirebaseAuth mAuth;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String formattedBirthdate;


    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = (FormEditText) view.findViewById(R.id.etxt_email);
        password = (FormEditText) view.findViewById(R.id.etxt_password);
        passwordRepeat = (FormEditText) view.findViewById(R.id.etxt_password_confirm);
        birthdate = (FormEditText) view.findViewById(R.id.etxt_birthdate);
        birthdate.setInputType(InputType.TYPE_NULL);
        name = (FormEditText) view.findViewById(R.id.etxt_name);
        btnCreateAccount = (Button) view.findViewById(R.id.btn_create_account);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidForm()){

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SIGNIN", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        //TODO send user information to firebase

                                        Intent intent = new Intent(getContext(), CheckIfBornedActivity.class);
                                        intent.putExtra("NAME",name.getText().toString());
                                        intent.putExtra("EMAIL",email.getText().toString());
                                        intent.putExtra("BIRTHDATE",formattedBirthdate);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SIGNIN", "createUserWithEmail:failure", task.getException());
                                        try {
                                            throw task.getException();
                                        } catch(FirebaseAuthUserCollisionException e) {
                                            Toast.makeText(getActivity(), "El email ya existe.",
                                                    Toast.LENGTH_SHORT).show();
                                        } catch(Exception e) {
                                            Toast.makeText(getActivity(), "Hubo un error al crear su usuario.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            });
                }
            }
        });

        birthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(v.getContext(),R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            formattedBirthdate = dateFormatter.format(newDate.getTime());
                            birthdate.setText(formattedBirthdate);
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                }
            }
        });
    }

    private boolean isValidForm(){
        boolean isValid = true;
        FormEditText[] allFields	= { email, password, passwordRepeat, birthdate, name };
        passwordRepeat.addValidator(new RepeatPasswordValidator(password));
        for (FormEditText field: allFields) {
            isValid = field.testValidity() && isValid;
        }
        return isValid;
    }

}
