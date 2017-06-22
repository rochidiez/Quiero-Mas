package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.activity.LoginActivity;
import com.android.quieromas.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninFragment extends BaseFragment {


    private Button btnSignin;
    private FormEditText email;
    private FormEditText password;
    private FirebaseAuth mAuth;



    public SigninFragment() {
        // Required empty public constructor
    }


    public static SigninFragment newInstance() {
        SigninFragment fragment = new SigninFragment();
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
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = (FormEditText) view.findViewById(R.id.etxt_signin_email);
        password = (FormEditText) view.findViewById(R.id.etxt_signin_password);
        btnSignin = (Button) view.findViewById(R.id.btn_signin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HOLAAAA",email.getText().toString());
                boolean validations = true;
                FormEditText[] allFields	= { email, password };
                for (FormEditText field: allFields) {
                    validations = field.testValidity() && validations;
                }

                if(validations){
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getActivity(), "OK",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

}
