package com.android.quieromas.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.validator.RepeatPasswordValidator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstUseActivity extends AuthActivity {

    private Button btnContinue;
    private Button btnUploadPicture;
    private CircleImageView imgProfile;
    private FormEditText txtName;
    private FormEditText txtNickname;
    private FormEditText txtBirthdate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

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
                //TODO Image picker
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
}
