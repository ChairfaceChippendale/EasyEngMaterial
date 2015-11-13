package com.ujujzk.easyengmaterial.eeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.Dictionary;


public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int TARGET_SDK = 21;

    private Toolbar toolBar;
    private CardView grammarTile, vocabularyTile, dictionaryTile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Main activity was created");

        if (Build.VERSION.SDK_INT >= TARGET_SDK) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.main_act_transition));
        }

        toolBar = (Toolbar) findViewById(R.id.main_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);

        grammarTile = (CardView) findViewById(R.id.main_act_gramm_tile);
        vocabularyTile = (CardView) findViewById(R.id.main_act_vocab_tile);
        dictionaryTile = (CardView) findViewById(R.id.main_act_dict_tile);

        View.OnClickListener oclTile = new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.main_act_gramm_tile:
                        startActivityWithAnimation(v, GrammarActivity.class, R.id.main_act_gramm_tile_img);
                        break;

                    case R.id.main_act_vocab_tile:
                        startActivityWithAnimation(v, VocabularyActivity.class, R.id.main_act_vocab_tile_img);
                        break;

                    case R.id.main_act_dict_tile:
                        startActivityWithAnimation(v, DictionaryActivity.class, R.id.main_act_dict_tile_img);
                        break;

                    default:
                        break;
                }
            }
        };

        grammarTile.setOnClickListener(oclTile);
        vocabularyTile.setOnClickListener(oclTile);
        dictionaryTile.setOnClickListener(oclTile);

    }

    private void startActivityWithAnimation(View v, Class selectedClass, int imagViewId) {

        Intent intent = new Intent(MainActivity.this, selectedClass);
        if (Build.VERSION.SDK_INT >= TARGET_SDK) {

            ImageView titleImage = (ImageView) v.findViewById(imagViewId);
            View navigationBar = findViewById(android.R.id.navigationBarBackground);
            View statusBar = findViewById(android.R.id.statusBarBackground);

            Pair<View, String> imagePair = Pair.create((View) titleImage, titleImage.getTransitionName());

            Pair<View, String> navPair = Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
            Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
            Pair<View, String> toolbarPair = Pair.create((View) toolBar, toolBar.getTransitionName());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imagePair, navPair, statusPair, toolbarPair);
            startActivity(intent, optionsCompat.toBundle());

        } else {
            startActivity(intent);
        }

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
        Log.d(TAG, "Main activity was destroyed");
    }


}
