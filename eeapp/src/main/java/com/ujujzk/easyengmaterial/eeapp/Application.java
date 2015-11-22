package com.ujujzk.easyengmaterial.eeapp;

import android.content.Context;
import android.util.Log;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.ujujzk.easyengmaterial.eeapp.dao.CrudDao;
import com.ujujzk.easyengmaterial.eeapp.dao.parse.ParseCloudCrudDaoImpl;
import com.ujujzk.easyengmaterial.eeapp.dao.parse.ParseLocalCrudDaoImpl;
import com.ujujzk.easyengmaterial.eeapp.model.*;

public class Application extends android.app.Application {

    private static final String APPLICATION_TAG = "applicationTag";

    private static Context context;

    public static Context getContext(){
        return context;
    }

    public static CrudDao<Card, String> cardLocalCrudDao;
    public static CrudDao<Card, String> cardCloudCrudDao;

    public static CrudDao<Pack, String> packLocalCrudDao;
    public static CrudDao<Pack, String> packCloudCrudDao;

    public static CrudDao<Task, String> taskLocalCrudDao;
    public static CrudDao<Task, String> taskCloudCrudDao;

    public static CrudDao<Topic, String> topicLocalCrudDao;
    public static CrudDao<Topic, String> topicCloudCrudDao;

    public static CrudDao<Rule, String> ruleLocalCrudDao;
    public static CrudDao<Rule, String> ruleCloudCrudDao;

    public static CrudDao<Answer, String> answerCloudCrudDao;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(APPLICATION_TAG, "Application was created");

        Parse.enableLocalDatastore(this);
        ParseCrashReporting.enable(this);
        Parse.initialize(this, "a2FaVXXRxCiY0r61U0nZ6hS6VhuSDcQfC32Vhium", "b2aaFgro20MWP8t1sRGbjdsRrJrwBBm78cSDKxD8");

        cardLocalCrudDao = new ParseLocalCrudDaoImpl<Card>(Card.class);
        cardCloudCrudDao = new ParseCloudCrudDaoImpl<Card>(Card.class);

        packLocalCrudDao = new ParseLocalCrudDaoImpl<Pack>(Pack.class);
        packCloudCrudDao = new ParseCloudCrudDaoImpl<Pack>(Pack.class);

        taskLocalCrudDao = new ParseLocalCrudDaoImpl<Task>(Task.class);
        taskCloudCrudDao = new ParseCloudCrudDaoImpl<Task>(Task.class);

        topicLocalCrudDao = new ParseLocalCrudDaoImpl<Topic>(Topic.class);
        topicCloudCrudDao = new ParseCloudCrudDaoImpl<Topic>(Topic.class);

        ruleLocalCrudDao = new ParseLocalCrudDaoImpl<Rule>(Rule.class);
        ruleCloudCrudDao = new ParseCloudCrudDaoImpl<Rule>(Rule.class);

        answerCloudCrudDao = new ParseCloudCrudDaoImpl<Answer>(Answer.class);

    }
}
