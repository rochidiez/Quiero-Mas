package com.android.quieromas.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.validator.RepeatPasswordValidator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstUseActivity extends AuthActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnContinue;
    private Button btnUploadPicture;
    private CircleImageView imgProfile;
    private FormEditText txtName;
    private FormEditText txtNickname;
    private FormEditText txtBirthdate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private boolean hasChangedImage = false;

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

        txtBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            txtBirthdate.setText(dateFormatter.format(newDate.getTime()));
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
