package com.android.quieromas.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.quieromas.R;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.user.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AuthFbActivity extends BaseActivity {
    private static final String TAG = "AuthFbActivity";


    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private LoginButton btnLogin;
    private Button btnFacebook;
    private FirebaseDatabaseHelper firebaseDatabaseHelper;
    private User user;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnLogin = (LoginButton) findViewById(R.id.btn_login) ;
        btnFacebook = (Button) findViewById(R.id.btn_login_fb) ;

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        btnLogin.setReadPermissions("email", "public_profile");
        btnLogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                Toast.makeText(AuthFbActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                Toast.makeText(AuthFbActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v) {
        if (v == btnFacebook) {
            btnLogin.performClick();
        }
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            firebaseDatabaseHelper = new FirebaseDatabaseHelper();
                            firebaseDatabaseHelper.getCurrentUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    user = dataSnapshot.getValue(User.class);
                                    if(user == null){
                                        getUserInfoFromFb(token);
                                    }else if(user.bebe == null){
                                        goToBabyInfo();
                                    }else{
                                        goToMain();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
//
                        } else {

                            try {
                                throw task.getException();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(getApplicationContext(), "El email ya fue registrado, ingrese con su contraseña",
                                        Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Toast.makeText(getApplicationContext(), "Hubo un error al crear su usuario.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToBabyInfo(){
        Intent intent = new Intent(this, CheckIfBornedActivity.class);
        startActivity(intent);
    }

    private void getUserInfoFromFb(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());
                        String email;
                        String name;
                        String birthdate;
                        // Application code
                        try{
                            email = object.getString("email");
                        }catch (Exception e){
                            email = "noemail@noemail.com";
                        }
                        try{
                            name = object.getString("name");
                        }catch (Exception e){
                            name = "Facebook User";
                        }
                        try{
                            birthdate = object.getString("birthday");
                        }catch (Exception e){
                            birthdate = "01/01/1990";
                        }

                        Intent intent = new Intent(getApplicationContext(), CheckIfBornedActivity.class);
                        intent.putExtra("NAME",name);
                        intent.putExtra("EMAIL",email);
                        intent.putExtra("BIRTHDATE",birthdate);
                        startActivity(intent);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
