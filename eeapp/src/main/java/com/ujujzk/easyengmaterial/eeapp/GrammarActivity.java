package com.ujujzk.easyengmaterial.eeapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.github.clans.fab.FloatingActionButton;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.ujujzk.easyengmaterial.eeapp.model.Topic;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class GrammarActivity extends AppCompatActivity implements TopicListAdapter.TopicViewHolder.ClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = GrammarActivity.class.getSimpleName();

    public static final String SELECTED_TOPICS_IDS = "selectedTopicsIds";
    public static final String SELECTED_TOPIC_ID = "selectedTopicID";

    private Toolbar toolBar;
    private Drawer navigationDrawer = null;
    private RecyclerView topicList;
    private TopicListAdapter topicListAdapter;
    private CircularProgressView progressBar;
    private FloatingActionButton runTopicsFab;
    private Button noConnectionBtn;
    private View noConnectionMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);

        toolBar = (Toolbar) findViewById(R.id.gramm_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);
        navigationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolBar)
                .withTranslucentStatusBar(true)
                .withAccountHeader(
                        new AccountHeaderBuilder()
                                .withActivity(this)
                                .withHeaderBackground(R.drawable.img_dict)
                                .build()
                )
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.title_activity_dictionary)
                                .withIcon(GoogleMaterial.Icon.gmd_chrome_reader_mode)
                                .withIdentifier(Application.IDENTIFIER_DICTIONARY),
                        new PrimaryDrawerItem()
                                .withName(R.string.title_activity_vocabulary)
                                .withIcon(GoogleMaterial.Icon.gmd_style)
                                .withIdentifier(Application.IDENTIFIER_VOCABULARY),
                        new PrimaryDrawerItem()
                                .withName(R.string.title_activity_grammar)
                                .withIcon(GoogleMaterial.Icon.gmd_class)
                                .withIdentifier(Application.IDENTIFIER_GRAMMAR),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.title_activity_about)
                                .withIcon(GoogleMaterial.Icon.gmd_info)
                                .withIdentifier(Application.IDENTIFIER_ABOUT),
                        new SecondaryDrawerItem()
                                .withName(R.string.title_share)
                                .withIcon(GoogleMaterial.Icon.gmd_share)
                                .withIdentifier(Application.IDENTIFIER_SHARE),
                        new SecondaryDrawerItem()
                                .withName(R.string.title_feedback)
                                .withIcon(GoogleMaterial.Icon.gmd_feedback)
                                .withIdentifier(Application.IDENTIFIER_FEEDBACK),
                        new SecondaryDrawerItem()
                                .withName(R.string.title_activity_settings)
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                                .withIdentifier(Application.IDENTIFIER_SETTING)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch(drawerItem.getIdentifier()){
                            case Application.IDENTIFIER_DICTIONARY:
                                startActivity(new Intent(GrammarActivity.this, DictionaryActivity.class));
                                finish();
                                break;
                            case Application.IDENTIFIER_VOCABULARY:
                                startActivity(new Intent(GrammarActivity.this, VocabularyActivity.class));
                                finish();
                                break;
                            case Application.IDENTIFIER_GRAMMAR:
                                break;
                            case Application.IDENTIFIER_ABOUT:
                                startActivity(new Intent(GrammarActivity.this, AboutActivity.class));
                                break;
                            case Application.IDENTIFIER_SHARE:
                                sendSharingMassage(getResources().getText(R.string.sharing_massage).toString());
                                break;
                            case Application.IDENTIFIER_FEEDBACK:
                                sendFeedBack("I like this app");
                                break;
                            case Application.IDENTIFIER_SETTING:
                                startActivity(new Intent(GrammarActivity.this, SettingsActivity.class));
                                break;
                            default:
                                break;
                        }
                        navigationDrawer.closeDrawer();
                        return true;
                    }
                })
                .build();
        navigationDrawer.setSelection(Application.IDENTIFIER_GRAMMAR);

        progressBar = (CircularProgressView) findViewById(R.id.gramm_act_progress_bar);
        noConnectionBtn = (Button) findViewById(R.id.no_connect_btn);
        noConnectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        noConnectionMsg = findViewById(R.id.gramm_act_no_connect);

        topicList = (RecyclerView) findViewById(R.id.gramm_act_rv_topic_list);
        topicList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
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
        loadData();
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


    private void loadData() {

        if (isNetworkConnected()) {

            if (topicListAdapter.isTopicListEmpty()) {

                new AsyncTask<Void, Void, List<Topic>>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressBar.setVisibility(View.VISIBLE);
                        topicList.setVisibility(View.GONE);
                        noConnectionMsg.setVisibility(View.GONE);
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
                        noConnectionMsg.setVisibility(View.GONE);
                    }
                }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            }
        } else {
            progressBar.setVisibility(View.GONE);
            topicList.setVisibility(View.GONE);
            noConnectionMsg.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void sendSharingMassage(String massage){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, massage);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void  sendFeedBack(String massage){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("text/email");
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { getResources().getString(R.string.feed_back_email) });
        email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feed_back_subject));
        email.putExtra(Intent.EXTRA_TEXT, massage);
        startActivity(Intent.createChooser(email, getResources().getString(R.string.feed_back_title)));
    }
}
