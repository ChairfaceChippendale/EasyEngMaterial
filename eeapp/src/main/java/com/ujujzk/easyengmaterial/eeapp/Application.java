package com.ujujzk.easyengmaterial.eeapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.util.Pair;
import android.util.Log;
import com.github.aleksandrsavosh.simplestore.SimpleStore;
import com.github.aleksandrsavosh.simplestore.SimpleStoreManager;
import com.ujujzk.easyengmaterial.eeapp.model.*;

import java.io.File;
import java.util.HashSet;

public class Application extends android.app.Application {

    private static final String APPLICATION_TAG = "applicationTag";

    //Navigation Drawer item identifiers
    public static final int IDENTIFIER_DICTIONARY = 1;
    public static final int IDENTIFIER_VOCABULARY = 2;
    public static final int IDENTIFIER_GRAMMAR = 3;
    public static final int IDENTIFIER_ABOUT = 4;
    public static final int IDENTIFIER_SHARE = 6;
    public static final int IDENTIFIER_FEEDBACK = 7;
    public static final int IDENTIFIER_SETTING = 5;
    public static final int IDENTIFIER_CLEAR_HISTORY = 11;

    private static Context context;

    static SimpleStoreManager storeManager;
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
            add(Dictionary.class);
            add(Word.class);
            add(Article.class);
        }
        });

        storeManager.useLog(true);
        storeManager.initLocalStore(33);
        localStore = storeManager.getLocalStore();

//        localStore = new SQLiteSimpleStoreImpl();
//        localStore = (SimpleStore) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{SimpleStore.class}, new TransactionProxy(localStore, storeManager.getSqLiteHelper()));
//        return (SimpleStore)Proxy.newProxyInstance(simpleStore1.getClass().getClassLoader(), new Class[]{SimpleStore.class}, new LogProxy(simpleStore1));

        storeManager.initCloudStore("a2FaVXXRxCiY0r61U0nZ6hS6VhuSDcQfC32Vhium", "b2aaFgro20MWP8t1sRGbjdsRrJrwBBm78cSDKxD8");
        cloudStore = storeManager.getCloudStore();

        //Parse.initialize(this, "a2FaVXXRxCiY0r61U0nZ6hS6VhuSDcQfC32Vhium", "b2aaFgro20MWP8t1sRGbjdsRrJrwBBm78cSDKxD8");
        //ParseCrashReporting.enable(this);



        createAppFolder();


    }

    private void createAppFolder() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            Log.d("Application", "SD-card is not accessible");
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        path = new File(path.getAbsolutePath() + "/" + getResources().getString(R.string.app_name));

        if (!path.exists()) {
            path.mkdirs();
        }

    }


    public static SimpleStoreManager getStoreManager () {
        return storeManager;
    }

}

