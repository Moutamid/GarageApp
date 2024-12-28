package com.moutamid.garageapp.helper;

import android.app.Application;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);
    }
}