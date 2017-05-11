package com.android.quieromas.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class AuthActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{

    private FirebaseAuth mAuth;
    private static final String TAG = "AuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        checkIfUserIsLogged();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        checkIfUserIsLogged();
    }

    private void checkIfUserIsLogged(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


}
