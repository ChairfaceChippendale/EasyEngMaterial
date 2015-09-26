package com.ujujzk.easyengmaterial.eeapp.util;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import com.ujujzk.easyengmaterial.eeapp.MainActivity;
import com.ujujzk.easyengmaterial.eeapp.R;

public class ActivityUtil {

    public static void setTheme(Activity activity){
        Log.d(ActivityUtil.class.getName(), "--- === SET DEFAULT PREFERENCES === ---");
        PreferenceManager.setDefaultValues(activity, R.xml.preferences, false);

        String themeName = PreferenceManager.getDefaultSharedPreferences(activity).getString("pref_list_themes", "none");
        Log.d(ActivityUtil.class.getName(), "THEME: " + themeName);
        if(themeName.equals(activity.getResources().getString(R.string.theme_light))){
            activity.setTheme(R.style.Theme_Light_AppTheme);
        } else if(themeName.equals(activity.getResources().getString(R.string.theme_dark))){
            activity.setTheme(R.style.Theme_Dark_AppTheme);
        } else {
            activity.setTheme(R.style.Theme_Light_AppTheme);// if no theme found
        }
    }

    public static void checkIfThemeChanged(Activity activity) {
//        activity.getTheme();


    }
}