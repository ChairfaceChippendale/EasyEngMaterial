package com.ujujzk.easyengmaterial.eeapp;

import android.content.Intent;
import android.os.Build;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;


//http://translate.google.ru/translate_a/t?client=x&text={TEXT}&sl={LANG_FROM}&tl={LANG_TO}
//http://geektimes.ru/post/120161/
//http://web.archive.org/web/20110720142541/http://basharkokash.com/post/2010/04/19/Bing-Translator-for-developers.aspx

public class DictionaryActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private static final String TAG = DictionaryActivity.class.getSimpleName();

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= MainActivity.TARGET_SDK){
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.main_act_transition));
        }
        setContentView(R.layout.activity_dictionary);

        toolBar = (Toolbar) findViewById(R.id.dict_act_app_bar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dictionary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.dict_act_action_settings:
                startActivity(new Intent(DictionaryActivity.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
