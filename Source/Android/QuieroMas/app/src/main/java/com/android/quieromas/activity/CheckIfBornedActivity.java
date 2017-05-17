package com.android.quieromas.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.fragment.VideoFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CheckIfBornedActivity extends AppCompatActivity implements VideoFragment.OnFragmentInteractionListener {

    private static final String TAG = "CheckIfBornedActivity";
    private Button btnIsBorned;
    private Button btnIsNotBorned;
    private View expectedDateView;
    private Button btnExpectedDateContinue;
    private FormEditText txtExpectedDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_if_borned);

        btnIsBorned = (Button) findViewById(R.id.btn_is_borned_yes);
        btnIsNotBorned = (Button) findViewById(R.id.btn_is_borned_no);
        expectedDateView = (FrameLayout) findViewById(R.id.expected_date_view);
        btnExpectedDateContinue = (Button) findViewById(R.id.btn_expected_date_continue);
        txtExpectedDate = (FormEditText) findViewById(R.id.etxt_expected_date);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        btnIsNotBorned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expectedDateView.setVisibility(View.VISIBLE);
            }
        });

        btnIsBorned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtExpectedDate.testValidity()){
                    Intent intent = new Intent(view.getContext(), FirstUseActivity.class);
                    startActivity(intent);
                }
            }
        });

        txtExpectedDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            txtExpectedDate.setText(dateFormatter.format(newDate.getTime()));
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                }
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri){
        Log.w(TAG, "fragment interaction");
    }
}
