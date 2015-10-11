package com.ujujzk.easyengmaterial.eeapp.util;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.ujujzk.easyengmaterial.eeapp.MainActivity;
import com.ujujzk.easyengmaterial.eeapp.R;

public class ActivityUtil {

    public static void setTheme(Activity activity) {
        Log.d(ActivityUtil.class.getName(), "--- === SET DEFAULT PREFERENCES === ---");
        PreferenceManager.setDefaultValues(activity, R.xml.preferences, false);

        String themeName = PreferenceManager.getDefaultSharedPreferences(activity).getString("pref_list_themes", "none");
        Log.d(ActivityUtil.class.getName(), "THEME: " + themeName);
        if (themeName.equals(activity.getResources().getString(R.string.theme_light))) {
            activity.setTheme(R.style.Theme_Light_AppTheme);

            if (activity.findViewById(R.id.main_act_app_bar) != null) {
                activity.findViewById(R.id.main_act_app_bar).setBackgroundColor(ContextCompat.getColor(activity, R.color.toolbar_bgr_light));
            }

        } else if (themeName.equals(activity.getResources().getString(R.string.theme_dark))) {
            activity.setTheme(R.style.Theme_Dark_AppTheme);

            if (activity.findViewById(R.id.main_act_app_bar) != null) {
                activity.findViewById(R.id.main_act_app_bar).setBackgroundColor(ContextCompat.getColor(activity, R.color.toolbar_bgr_dark));
            }

        } else {
            activity.setTheme(R.style.Theme_Light_AppTheme);// if no theme found
        }
    }

    public static void setToolbarColor(Activity activity, int toolbarId) {
        String themeName = PreferenceManager.getDefaultSharedPreferences(activity).getString("pref_list_themes", "none");

        if (activity.findViewById(toolbarId) != null) {

            if (themeName.equals(activity.getResources().getString(R.string.theme_light))) {

                activity.findViewById(toolbarId).setBackgroundColor(ContextCompat.getColor(activity, R.color.toolbar_bgr_light));

            } else if (themeName.equals(activity.getResources().getString(R.string.theme_dark))) {

                activity.findViewById(toolbarId).setBackgroundColor(ContextCompat.getColor(activity, R.color.toolbar_bgr_dark));

            } else {

                activity.findViewById(toolbarId).setBackgroundColor(ContextCompat.getColor(activity, R.color.toolbar_bgr_light));

            }
        }

    }

    public static void checkIfThemeChanged(Activity activity) {
//        activity.getTheme();


    }
}