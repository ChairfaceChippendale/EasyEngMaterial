package com.ujujzk.easyengmaterial.eeapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.Dictionary;


public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACT_TAG = "mainActTag";


    private Toolbar toolBar;
    private CardView grammarTile, vocabularyTile, dictionaryTile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MAIN_ACT_TAG, "onCreate");

        toolBar = (Toolbar) findViewById(R.id.main_act_app_bar);
        setSupportActionBar(toolBar);

        grammarTile = (CardView) findViewById(R.id.main_act_gramm_tile);
        vocabularyTile = (CardView) findViewById(R.id.main_act_vocab_tile);
        dictionaryTile = (CardView) findViewById(R.id.main_act_dict_tile);

        View.OnClickListener oclTile = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.main_act_gramm_tile:
                        startActivity(new Intent(MainActivity.this, GrammarActivity.class));
                        break;

                    case R.id.main_act_vocab_tile:
                        startActivity(new Intent(MainActivity.this, VocabularyActivity.class));
                        break;

                    case R.id.main_act_dict_tile:
                        startActivity(new Intent(MainActivity.this, DictionaryActivity.class));
                        break;
                }

            }
        };

        grammarTile.setOnClickListener(oclTile);
        vocabularyTile.setOnClickListener(oclTile);
        dictionaryTile.setOnClickListener(oclTile);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.main_act_action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;

            case R.id.main_act_action_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MAIN_ACT_TAG, "onDestroy");
    }

}
