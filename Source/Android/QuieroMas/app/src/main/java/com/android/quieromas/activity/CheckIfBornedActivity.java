package com.android.quieromas.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.android.quieromas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckIfBornedActivity extends AppCompatActivity {

    private Button btnIsBorned;
    private Button btnIsNotBorned;
    private View expectedDateView;
    private Button btnExpectedDateContinue;
    private EditText txtExpectedDate;
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
        txtExpectedDate = (EditText) findViewById(R.id.etxt_expected_date);

        btnIsNotBorned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expectedDateView.setVisibility(View.VISIBLE);
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
}
