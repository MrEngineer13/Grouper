package com.mrengineer13.grouper;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Jon on 11/11/17.
 */

public class GrouperApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        MultiDex.install(this);
    }
}
