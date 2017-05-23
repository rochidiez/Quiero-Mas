package com.android.quieromas.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.api.FirebaseFunctionApi;
import com.android.quieromas.api.ServiceFactory;
import com.android.quieromas.validator.RepeatPasswordValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import okhttp3.ResponseBody;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FirstUseActivity extends AuthActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "FirstUserActivity";
    private Button btnContinue;
    private Button btnUploadPicture;
    private CircleImageView imgProfile;
    private FormEditText txtName;
    private FormEditText txtNickname;
    private FormEditText txtBirthdate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private boolean hasChangedImage = false;
    private String formattedBabybirthdate;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_use);

        btnContinue = (Button) findViewById(R.id.btn_first_use_continue);
        btnUploadPicture =(Button) findViewById(R.id.btn_first_use_upload_photo);
        imgProfile = (CircleImageView) findViewById(R.id.img_first_use_profile);
        txtName = (FormEditText) findViewById(R.id.etxt_first_use_baby_name);
        txtNickname = (FormEditText) findViewById(R.id.etxt_first_use_baby_nickname);
        txtBirthdate = (FormEditText) findViewById(R.id.etxt_first_use_baby_birthdate);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        imgProfile.bringToFront();

        txtBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(v.getContext(),R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            formattedBabybirthdate = dateFormatter.format(newDate.getTime());
                            txtBirthdate.setText(formattedBabybirthdate);
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isValidForm()){
                    //TODO: mandar la info por firebase y despues chequear si es mayor de 6 meses

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//                            .build();
                    String text = buildQueryParameter(user.getUid(),"testing","15/4/1990",
                            "lucas@email.com",formattedBabybirthdate,txtName.getText().toString(),
                            txtNickname.getText().toString());
                   api.register(text)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ResponseBody>() {
                               @Override
                               public final void onCompleted() {
                               }

                               @Override
                               public final void onError(Throwable e) {
                                   Log.e(TAG, e.getMessage());
                                   Toast.makeText(getApplicationContext(),"gg",Toast.LENGTH_LONG).show();
                               }

                               @Override
                               public final void onNext(ResponseBody rb){

                                   Log.d(TAG,"DS");
                                   Toast.makeText(getApplicationContext(),"ff",Toast.LENGTH_LONG).show();
                               }
                   });
                }
            }
        });

        btnUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), PICK_IMAGE_REQUEST);

            }
        });
    }

    private String buildQueryParameter(String uid, String name, String birthdate, String email, String babyBirthdate, String babyName, String babyNickname){
        //https://us-central1-quiero-mas.cloudfunctions.net/registrar
        // ?text=IBObsbDGMNRrC1ra5U50QpBhuyE3+lucas+10/5/1990+lucasputerman@gmail.com+10/5/2016+bebe+bebito
        StringBuilder builder = new StringBuilder();
        builder.append(uid);
        builder.append("+");

        String[] splittedName = name.split(" ");
        for(int i = 0; i < splittedName.length; i++){
            builder.append(splittedName[i]);
            if(i < splittedName.length -1){
                builder.append("-");
            }
        }
        builder.append("+");
        builder.append(birthdate);
        builder.append("+");
        builder.append(email);
        builder.append("+");
        builder.append(babyBirthdate);
        builder.append("+");
        String[] splittedBabyName = babyName.split(" ");
        for(int i = 0; i < splittedBabyName.length; i++){
            builder.append(splittedBabyName[i]);
            if(i < splittedBabyName.length -1){
                builder.append("-");
            }
        }
        builder.append("+");
        String[] splittedBabyNickname = babyNickname.split(" ");
        for(int i = 0; i < splittedBabyNickname.length; i++){
            builder.append(splittedBabyNickname[i]);
            if(i < splittedBabyNickname.length -1){
                builder.append("-");
            }
        }

        return builder.toString();

    }

    private boolean isValidForm(){
        boolean isValid = true;
        FormEditText[] allFields	= { txtName, txtBirthdate, txtNickname};
        for (FormEditText field: allFields) {
            isValid = field.testValidity() && isValid;
        }
        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgProfile.setImageBitmap(bitmap);
                hasChangedImage = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
