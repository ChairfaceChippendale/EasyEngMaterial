package com.ujujzk.easyengmaterial.eeapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.ujujzk.easyengmaterial.eeapp.model.Card;
import com.ujujzk.easyengmaterial.eeapp.model.MOC;
import com.ujujzk.easyengmaterial.eeapp.model.Pack;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;


public class VocabularyActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private static final String TAG = VocabularyActivity.class.getSimpleName();

    private List<Pack> packs; //TODO

    Toolbar toolBar;
    private RecyclerView packsList;
    private ProgressBar progressBar;
    private ArrayList<Card> aggregateCardsToLearn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= MainActivity.TARGET_SDK) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.main_act_transition));
        }
        setContentView(R.layout.activity_vocabulary);

        toolBar = (Toolbar) findViewById(R.id.vocab_act_app_bar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.vocab_act_prorgress_bar);
        packsList = (RecyclerView) findViewById(R.id.vocab_act_rv_packs_list);

        //packsList.setHasFixedSize(true); //optimizations if changes in adapter content cannot change the size of the RecyclerView itself.

        packs = MOC.getPacksMOC(); //TODO

        GridLayoutManager packsGridLayoutManager;
        if (isTablet(this)){
            packsGridLayoutManager = new GridLayoutManager(this,2);
        }else{
            packsGridLayoutManager = new GridLayoutManager(this,1);
        }
        packsList.setLayoutManager(packsGridLayoutManager);

        PacksListAdapter packsListAdapter = new PacksListAdapter(packs);
        packsList.setAdapter(packsListAdapter);
        packsList.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vocabulary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.vocab_act_action_settings:
                startActivity(new Intent(VocabularyActivity.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.vocab_act_action_add_pack:
                // TODO
                return true;

            case R.id.vocab_act_action_cloud_download:
                // TODO
                return true;

            case R.id.vocab_act_action_remove_pack:
                // TODO
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
