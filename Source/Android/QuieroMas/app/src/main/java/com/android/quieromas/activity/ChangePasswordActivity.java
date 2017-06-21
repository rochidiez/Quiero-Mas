package com.android.quieromas.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.validator.RepeatPasswordValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AuthActivity {

    private static final String TAG = "ChangePasswordActivity";

    private FormEditText oldPassword;
    private FormEditText newPasswordRepeat;
    private FormEditText newPassword;
    private Button btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Cambiar Contraseña");
        setContentView(R.layout.activity_change_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        oldPassword = (FormEditText) findViewById(R.id.etxt_password_change_old);
        newPassword = (FormEditText) findViewById(R.id.etxt_password_change_new);
        newPasswordRepeat = (FormEditText) findViewById(R.id.etxt_password_change_repeat);


        btnSaveChanges = (Button) findViewById(R.id.btn_password_change_save);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidForm()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String newPasswordString = newPassword.getText().toString();

                    user.updatePassword(newPasswordString)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated.");
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isValidForm(){
        boolean isValid = true;
        FormEditText[] allFields	= { oldPassword, newPassword, newPasswordRepeat};
        newPasswordRepeat.addValidator(new RepeatPasswordValidator(newPassword));
        for (FormEditText field: allFields) {
            isValid = field.testValidity() && isValid;
        }
        return isValid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
