package com.example.aniket.demo1_1;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
