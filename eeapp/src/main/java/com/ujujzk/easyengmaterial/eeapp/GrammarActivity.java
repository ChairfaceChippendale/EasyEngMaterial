package com.ujujzk.easyengmaterial.eeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.model.Topic;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;


public class GrammarActivity extends AppCompatActivity implements TopicListAdapter.TopicViewHolder.ClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = GrammarActivity.class.getSimpleName();

    public static final String SELECTED_TOPICS_IDS = "selectedTopicsIds";
    public static final String SELECTED_TOPIC_ID = "selectedTopicID";

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
        topicList.setAdapter(topicListAdapter);
        topicList.setLayoutManager(new LinearLayoutManager(this));
        topicList.setItemAnimator(new DefaultItemAnimator());

        runTopicsFab = (FloatingActionButton) findViewById(R.id.gramm_act_fab);
        runTopicsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> ids = topicListAdapter.getSelectedTopicsIds(topicListAdapter.getSelectedItems());

                if (ids.size() > 0) {
                    Intent intent = new Intent(GrammarActivity.this, ExerciseActivity.class);
                    intent.putStringArrayListExtra(SELECTED_TOPICS_IDS, (ArrayList<String>)ids);
                    startActivity(intent);
                }

                topicListAdapter.clearSelection();
                runTopicsFab.hide(true);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (topicListAdapter.isTopicListEmpty()) {

            new AsyncTask<Void, Void, List<Topic>>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                    topicList.setVisibility(View.GONE);
                }

                @Override
                protected List<Topic> doInBackground(Void... params) {
                    return Application.cloudStore.readAll(Topic.class);
                }

                @Override
                protected void onPostExecute(List<Topic> topics) {
                    topicListAdapter.addTopics(topics);
                    progressBar.setVisibility(View.GONE);
                    topicList.setVisibility(View.VISIBLE);
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }
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
                if (topicListAdapter.getSelectedItemCount() == 1) {
                    List<String> ids = topicListAdapter.getSelectedTopicsIds(topicListAdapter.getSelectedItems());
                    if (ids.size() > 0) {
                        Intent intent = new Intent(GrammarActivity.this, RuleActivity.class);
                        intent.putExtra(SELECTED_TOPIC_ID, ids.get(0));
                        startActivity(intent);
                    }
                }
                runTopicsFab.hide(true);
                topicListAdapter.clearSelection();
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

        topicListAdapter.toggleSelection(position);

        if (topicListAdapter.getSelectedItemCount() > 0) {
            if (runTopicsFab.isHidden()) {
                runTopicsFab.show(true);
            }
        } else {
            if (!runTopicsFab.isHidden()) {
                runTopicsFab.hide(true);
            }
        }
    }

    @Override
    public boolean onItemLongClicked(int position) {
        return true;
    }
}
