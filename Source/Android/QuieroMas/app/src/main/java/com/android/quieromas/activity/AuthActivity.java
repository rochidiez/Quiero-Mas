package com.android.quieromas.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.android.quieromas.api.FirebaseFunctionApi;
import com.android.quieromas.api.ServiceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class AuthActivity extends BaseActivity implements FirebaseAuth.AuthStateListener{

    FirebaseAuth mAuth;
    private static final String TAG = "AuthActivity";
    FirebaseFunctionApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        api = ServiceFactory.createRetrofitService(FirebaseFunctionApi.class, FirebaseFunctionApi.SERVICE_ENDPOINT);
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
            userLoggedEvent();
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    void userLoggedEvent(){}


}
