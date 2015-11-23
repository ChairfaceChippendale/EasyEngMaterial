package com.ujujzk.easyengmaterial.eeapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.model.Card;
import com.ujujzk.easyengmaterial.eeapp.model.Pack;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import java.util.ArrayList;
import java.util.List;

public class VocabularyActivity extends AppCompatActivity implements PacksListAdapter.PackViewHolder.ClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = VocabularyActivity.class.getSimpleName();

    private static final int GRIDS_ON_TABLET = 2;
    private static final int GRIDS_ON_PHONE = 1;

    public static final String SELECTED_PACK_ID = "selectedPackId";
    public static final String SELECTED_CARD_IDS = "selectedCardIds";

    private Toolbar toolBar;
    private RecyclerView packList;
    private PacksListAdapter packListAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton runCardsFab;
    private MaterialDialog confirmPackRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= MainActivity.TARGET_SDK) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.main_act_transition));
        }
        setContentView(R.layout.activity_vocabulary);

        toolBar = (Toolbar) findViewById(R.id.vocab_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.vocab_act_progress_bar);

        packList = (RecyclerView) findViewById(R.id.vocab_act_rv_packs_list);
        packListAdapter = new PacksListAdapter(this);
        packList.setAdapter(packListAdapter);
        StaggeredGridLayoutManager packsGridLayoutManager;
        if (isTablet(this)) {
            packsGridLayoutManager = new StaggeredGridLayoutManager(GRIDS_ON_TABLET, StaggeredGridLayoutManager.VERTICAL);
        } else {
            packsGridLayoutManager = new StaggeredGridLayoutManager(GRIDS_ON_PHONE, StaggeredGridLayoutManager.VERTICAL);
        }
        packList.setLayoutManager(packsGridLayoutManager);
        packList.setItemAnimator(new DefaultItemAnimator());

        runCardsFab = (FloatingActionButton) findViewById(R.id.vacab_act_fab);
        runCardsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Long> ids = packListAdapter.getSelectedPacksCardsIds(packListAdapter.getSelectedItems());

                if (ids.size() > 0) {
                    Intent intent = new Intent(VocabularyActivity.this, LearnWordActivity.class);
                    intent.putExtra(SELECTED_CARD_IDS, (ArrayList<Long>)ids);
                    startActivity(intent);
                }

                packListAdapter.clearSelection();
                runCardsFab.hide(true);
            }
        });

        confirmPackRemove = new MaterialDialog.Builder(this)
                .content(R.string.vocab_act_pack_remove_confirm_question)
                .positiveText(R.string.vocab_act_pack_remove_confirm_btn)
                .negativeText(R.string.vocab_act_pack_remove_cancel_btn)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        packListAdapter.removePacks(packListAdapter.getSelectedItems());
                        packListAdapter.clearSelection();
                        confirmPackRemove.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        packListAdapter.clearSelection();
                        confirmPackRemove.dismiss();
                    }
                })
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        new AsyncTask<Void, Void, List<Pack>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                packList.setVisibility(View.GONE);
            }

            @Override
            protected List<Pack> doInBackground(Void... params) {
                return Application.localStore.readAllWithRelations(Pack.class);
            }

            @Override
            protected void onPostExecute(List<Pack> packs) {

                packListAdapter.updatePacks(packs);
                progressBar.setVisibility(View.GONE);
                packList.setVisibility(View.VISIBLE);
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

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

                packListAdapter.addPack( Application.localStore.create( new Pack ("New pack",new ArrayList<Card>()) ) );
                return true;

            case R.id.vocab_act_action_cloud_download:

                new AsyncTask<Void,Void,List<Pack>>() {
                    @Override
                    protected List<Pack> doInBackground(Void... params) {
                        List<Pack> packsFromCloud = Application.cloudStore.readAllWithRelations(Pack.class);
                        if (packsFromCloud != null && !packsFromCloud.isEmpty()) {
                            for (Pack pack : packsFromCloud) {
                                Application.localStore.createWithRelations(pack);
                            }
                        }
                        return packsFromCloud;
                    }

                    @Override
                    protected void onPostExecute(List<Pack> packsFromCloud) {
                        if (packsFromCloud != null && !packsFromCloud.isEmpty()) {
                            packListAdapter.addPacks(packsFromCloud);
                        }
                    }
                }.execute();
                return true;

            case R.id.vocab_act_action_remove_pack:

                if (packListAdapter.getSelectedItemCount() > 0) {
                    confirmPackRemove.show();
                }
                if (!runCardsFab.isHidden()) {
                    runCardsFab.hide(true);
                }
                return true;

            case R.id.vocab_act_action_edit_pack:

                if (packListAdapter.getSelectedItemCount() == 1) {

                    List<Long> ids = packListAdapter.getSelectedPacksId(packListAdapter.getSelectedItems());
                    if (ids.size() > 0) {

                        Intent intent = new Intent(VocabularyActivity.this, EditPackActivity.class);
                        intent.putExtra(SELECTED_PACK_ID, ids.get(0));
                        startActivity(intent);
                    }
                }
                runCardsFab.hide(true);
                packListAdapter.clearSelection();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(int position) {

        packListAdapter.toggleSelection(position);

        if (packListAdapter.getSelectedItemCount() > 0) {
            if (runCardsFab.isHidden()) {
                runCardsFab.show(true);
            }
        } else {
            if (!runCardsFab.isHidden()) {
                runCardsFab.hide(true);
            }
        }
    }

    @Override
    public boolean onItemLongClicked(int position) {
        //TODO
        return true;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


}
