package com.android.quieromas.application;

import android.app.Application;

import com.android.quieromas.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by lucas on 17/5/17.
 */

public class QuieroMas extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Cera-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
