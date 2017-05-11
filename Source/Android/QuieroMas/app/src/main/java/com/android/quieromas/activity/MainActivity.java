package com.android.quieromas.activity;

import android.os.Bundle;

import com.android.quieromas.R;

public class MainActivity extends AuthActivity {

    private static int LOG_IN_CODE = 23;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

}
