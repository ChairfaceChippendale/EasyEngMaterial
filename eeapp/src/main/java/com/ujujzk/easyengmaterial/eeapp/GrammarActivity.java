package com.ujujzk.easyengmaterial.eeapp;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;


public class GrammarActivity extends AppCompatActivity implements TopicListAdapter.TopicViewHolder.ClickListener {

    private Toolbar toolBar;
    private RecyclerView topicList;
    private TopicListAdapter topicListAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton runTopicsFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= MainActivity.TARGET_SDK) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.main_act_transition));
        }
        setContentView(R.layout.activity_grammar);

        toolBar = (Toolbar) findViewById(R.id.gramm_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.gramm_act_progress_bar);

        topicList = (RecyclerView) findViewById(R.id.gramm_act_rv_topic_list);
        topicListAdapter = new TopicListAdapter(this);

        runTopicsFab = (FloatingActionButton) findViewById(R.id.gramm_act_fab);
        runTopicsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO

                runTopicsFab.hide(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grammar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.gramm_act_action_settings:
                startActivity(new Intent(GrammarActivity.this, SettingsActivity.class));
                return true;

            case R.id.gramm_act_action_rule:
                //TODO
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(int position) {


    }

    @Override
    public boolean onItemLongClicked(int position) {
        return true;
    }
}
