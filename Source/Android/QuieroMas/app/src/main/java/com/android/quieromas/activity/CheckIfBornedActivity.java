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
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.fragment.VideoFragment;
import com.android.quieromas.helper.QueryHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckIfBornedActivity extends AuthActivity implements VideoFragment.OnFragmentInteractionListener {

    private static final String TAG = "CheckIfBornedActivity";
    private Button btnIsBorned;
    private Button btnIsNotBorned;
    private View expectedDateView;
    private Button btnExpectedDateContinue;
    private FormEditText txtExpectedDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String formattedBabybirthdate;
    private String name;
    private String email;
    private String birthdate;

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("NAME");
            email = extras.getString("EMAIL");
            birthdate = extras.getString("BIRTHDATE");
        }


        btnIsNotBorned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIsNotBorned.setTextColor(getResources().getColor(R.color.orangePrimary));
                btnIsNotBorned.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                expectedDateView.setVisibility(View.VISIBLE);
            }
        });

        btnIsBorned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FirstUseActivity.class);
                intent.putExtra("NAME",name);
                intent.putExtra("EMAIL",email);
                intent.putExtra("BIRTHDATE",birthdate);
                startActivity(intent);
            }
        });

        btnExpectedDateContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtExpectedDate.testValidity()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String text = QueryHelper.buildQueryParameter(user.getUid(),name,email,
                           birthdate,formattedBabybirthdate,"", "");
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
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                }
            }
        });

        txtExpectedDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(v.getContext(),R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            formattedBabybirthdate = dateFormatter.format(newDate.getTime());
                            txtExpectedDate.setText(formattedBabybirthdate);
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
