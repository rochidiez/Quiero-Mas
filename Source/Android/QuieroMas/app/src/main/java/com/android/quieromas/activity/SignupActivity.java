package com.android.quieromas.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.android.quieromas.R;
import com.android.quieromas.fragment.ForgotPasswordFragment;
import com.android.quieromas.fragment.SigninFragment;
import com.android.quieromas.fragment.SignupFragment;
import com.android.quieromas.fragment.VideoFragment;

public class SignupActivity extends AppCompatActivity implements VideoFragment.OnFragmentInteractionListener,
        SignupFragment.OnFragmentInteractionListener, SigninFragment.OnFragmentInteractionListener, ForgotPasswordFragment.OnFragmentInteractionListener{

    private static final String TAG = "SignupActivity";

    private Button btnHasAccount;
    private View signinFragement;
    private View signupFragement;
    private View forgotPasswordFragment;

    private SignInState signInState = SignInState.SIGN_UP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        signinFragement = (View) findViewById(R.id.fragment_signin);
        signupFragement = (View) findViewById(R.id.fragment_signup);
        forgotPasswordFragment = (View) findViewById(R.id.fragment_forgot);
        signinFragement.setVisibility(View.GONE);
        forgotPasswordFragment.setVisibility(View.GONE);
        btnHasAccount = (Button) findViewById(R.id.btn_has_acconut);
        btnHasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFragmentSwitch();
            }
        });

        Button btnForgotPassword = (Button) findViewById(R.id.btn_forgot_password);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInState = SignInState.FORGOT_PASSWORD;
                forgotPasswordFragment.setVisibility(View.VISIBLE);
                signinFragement.setVisibility(View.GONE);
            }
        });

    }

    public void handleFragmentSwitch(){
        if (signInState == SignInState.SIGN_UP){
            btnHasAccount.setText("TODAVÍA NO TENGO CUENTA");
            signupFragement.setVisibility(View.GONE);
            signinFragement.setVisibility(View.VISIBLE);
            signInState = SignInState.SIGN_IN;
        }else{
            signInState = SignInState.SIGN_UP;
            signupFragement.setVisibility(View.VISIBLE);
            signinFragement.setVisibility(View.GONE);
            forgotPasswordFragment.setVisibility(View.GONE);
            btnHasAccount.setText("YA TENGO CUENTA");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        Log.w(TAG, "Hello");
    }

    public enum SignInState{
        SIGN_UP,
        SIGN_IN,
        FORGOT_PASSWORD
    }
}
