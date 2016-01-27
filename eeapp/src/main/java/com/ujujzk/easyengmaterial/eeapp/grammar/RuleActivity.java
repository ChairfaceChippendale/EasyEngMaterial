package com.ujujzk.easyengmaterial.eeapp.grammar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import com.github.clans.fab.FloatingActionButton;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Rule;
import com.ujujzk.easyengmaterial.eeapp.model.Topic;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;


public class RuleActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private static final String TAG = RuleActivity.class.getSimpleName();

    private Toolbar toolBar;
    private WebView ruleTextView;
    private CircularProgressView progressBar;
    private FloatingActionButton runTopicFab;
    private String topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        toolBar = (Toolbar) findViewById(R.id.rule_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (CircularProgressView) findViewById(R.id.rule_act_progress_bar);

        ruleTextView = (WebView) findViewById(R.id.rule_act_wv_rule_text);

        runTopicFab = (FloatingActionButton) findViewById(R.id.rule_act_fab);
        runTopicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> ids = new ArrayList<String>();
                ids.add(topicId);
                Intent intent = new Intent(RuleActivity.this, ExerciseActivity.class);
                intent.putStringArrayListExtra(GrammarActivity.SELECTED_TOPICS_IDS, (ArrayList<String>)ids);
                startActivity(intent);
                overridePendingTransition(R.animator.activity_appear_from_right, R.animator.activity_disappear_alpha); //custom activity transition animation
                finish();

            }
        });

        Intent intent = getIntent();
        topicId = intent.getStringExtra(GrammarActivity.SELECTED_TOPIC_ID);

        new AsyncTask<Void,Void,Pair<String,String>>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ruleTextView.setVisibility(View.GONE);
                runTopicFab.hide(false);
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            protected Pair<String,String> doInBackground(Void... params) {
                Topic topic = Application.cloudStore.read(topicId, Topic.class);
                String topicTitle = topic.getTitle();
                Rule rule = Application.cloudStore.read(topic.getRuleId(), Rule.class);
                String ruleString = rule.getRule();
                return new Pair<String, String>(topicTitle, ruleString);
            }
            @Override
            protected void onPostExecute(Pair<String,String> pair) {
                super.onPostExecute(pair);
                if (getSupportActionBar() != null && pair.first.length() > 0) {
                    getSupportActionBar().setTitle(pair.first);
                }
                ruleTextView.loadData(pair.second, "text/html", null);
                //ruleTextView.setBackgroundColor(getResources().getColor(R.color.main_window_bgr_light));
                ruleTextView.setBackgroundColor(ContextCompat.getColor(RuleActivity.this, R.color.main_window_bgr_light));
                //to get context follow there:
                //http://stackoverflow.com/questions/16920942/getting-context-in-asynctask
                ruleTextView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                runTopicFab.show(true);
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
        }
        return super.onOptionsItemSelected(item);
    }
}
