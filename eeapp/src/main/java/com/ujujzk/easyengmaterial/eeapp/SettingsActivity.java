package com.ujujzk.easyengmaterial.eeapp;

import android.os.Build;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.FrameLayout;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;


public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        if(Build.VERSION.SDK_INT >= 11){
            getFragmentManager().beginTransaction().replace(R.id.set_act_fl, new PreferenceFragment() {
                @Override
                public void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    addPreferencesFromResource(R.xml.preferences);
                }
            }).commit();
        } else {
//            addPreferencesFromResource(R.xml.preferences);
        }

        toolBar = (Toolbar) findViewById(R.id.set_act_app_bar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
