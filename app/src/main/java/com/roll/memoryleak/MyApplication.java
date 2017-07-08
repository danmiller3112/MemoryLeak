package com.roll.memoryleak;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by RDL on 01/07/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        LeakCanary.install(this);
    }
}
