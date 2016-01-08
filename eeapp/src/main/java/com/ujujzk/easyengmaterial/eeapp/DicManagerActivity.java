package com.ujujzk.easyengmaterial.eeapp;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DicManagerActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private static final String TAG = DicManagerActivity.class.getSimpleName();

    private Toolbar toolBar;
    private FloatingActionButton initializeNewDictionariesFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic_manager);

        toolBar = (Toolbar) findViewById(R.id.dic_manager_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeNewDictionariesFab = (FloatingActionButton) findViewById(R.id.dic_manager_act_fab);
        initializeNewDictionariesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO load new dictionaries from folder?

                initializeNewDictionaries();

            }
        });
    }

    private void initializeNewDictionaries() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //TODO Massage that SD-card is not accessible
            Log.d(TAG, "SD-card is not accessible");
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        path = new File(path.getAbsolutePath() + "/" + getResources().getString(R.string.app_name));

        if (!path.exists()) {
            path.mkdirs();
            //TODO Massage that there aren't new dictionaries
            Log.d(TAG, "There were no new dictionaries, a folder for dictionaries was mode");
            return;
        }

        List<File> files = Arrays.asList(path.listFiles());

        for (File file: files){
            if (file.getName().endsWith(".dsl")){
                addDictionaryFileToDataBase(file);
            }
        }

        if (files.isEmpty()){
            //TODO Massage that there aren't new dictionaries. Notice that dictionary must have expansion .dsl
            Log.d(TAG, "There were no new dictionaries");
            return;
        }

    }

    private void addDictionaryFileToDataBase (File file){
        //TODO Check if there already present current dictionary
        //TODO
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
        }

        return super.onOptionsItemSelected(item);
    }

}
