package com.ujujzk.easyengmaterial.eeapp;

import android.util.Log;
import com.parse.Parse;
import com.parse.ParseCrashReporting;

public class Application extends android.app.Application {

    private static final String APPLICATION_TAG = "applicationTag";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(APPLICATION_TAG, "Application was created");

        ParseCrashReporting.enable(this);
        Parse.initialize(this, "a2FaVXXRxCiY0r61U0nZ6hS6VhuSDcQfC32Vhium", "b2aaFgro20MWP8t1sRGbjdsRrJrwBBm78cSDKxD8");

    }
}
