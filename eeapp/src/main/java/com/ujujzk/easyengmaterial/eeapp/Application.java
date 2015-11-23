package com.ujujzk.easyengmaterial.eeapp;

import android.content.Context;
import android.util.Log;
import com.github.aleksandrsavosh.simplestore.SimpleStore;
import com.github.aleksandrsavosh.simplestore.SimpleStoreManager;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.ujujzk.easyengmaterial.eeapp.model.*;

import java.util.HashSet;

public class Application extends android.app.Application {

    private static final String APPLICATION_TAG = "applicationTag";

    private static Context context;

    SimpleStoreManager storeManager;
    public static SimpleStore<Long> localStore;
    public static SimpleStore<String> cloudStore;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(APPLICATION_TAG, "Application was created");

        storeManager = SimpleStoreManager.instance(this, new HashSet<Class>(){{
            add(Card.class);
            add(Pack.class);
            add(Topic.class);
            add(Rule.class);
            add(Task.class);
            add(Answer.class);
        }
        });

        storeManager.useLog(true);
        storeManager.initLocalStore(17);
        localStore = storeManager.getLocalStore();
        storeManager.initCloudStore("a2FaVXXRxCiY0r61U0nZ6hS6VhuSDcQfC32Vhium", "b2aaFgro20MWP8t1sRGbjdsRrJrwBBm78cSDKxD8");
        cloudStore = storeManager.getCloudStore();

        //Parse.initialize(this, "a2FaVXXRxCiY0r61U0nZ6hS6VhuSDcQfC32Vhium", "b2aaFgro20MWP8t1sRGbjdsRrJrwBBm78cSDKxD8");
        //ParseCrashReporting.enable(this);


    }
}
