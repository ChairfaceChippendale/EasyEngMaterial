package com.ujujzk.easyengmaterial.eeapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import java.util.ArrayList;


public class VocabularyActivity extends AppCompatActivity implements PacksListAdapter.PackViewHolder.ClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = VocabularyActivity.class.getSimpleName();

    private static final int GRIDS_ON_TABLET = 2;
    private static final int GRIDS_ON_PHONE = 1;

    private Toolbar toolBar;
    private RecyclerView packsList;
    private ProgressBar progressBar;
    private ArrayList<Card> aggregateCardsToLearn;

    private PacksListAdapter packsListAdapter;
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

        progressBar = (ProgressBar) findViewById(R.id.vocab_act_prorgress_bar);
        packsList = (RecyclerView) findViewById(R.id.vocab_act_rv_packs_list);

        packsListAdapter = new PacksListAdapter(this);
        packsList.setAdapter(packsListAdapter);

        StaggeredGridLayoutManager packsGridLayoutManager;
        if (isTablet(this)) {
            packsGridLayoutManager = new StaggeredGridLayoutManager(GRIDS_ON_TABLET, StaggeredGridLayoutManager.VERTICAL);
        } else {
            packsGridLayoutManager = new StaggeredGridLayoutManager(GRIDS_ON_PHONE, StaggeredGridLayoutManager.VERTICAL);
        }
        packsList.setLayoutManager(packsGridLayoutManager);

        packsList.setItemAnimator(new DefaultItemAnimator());

        runCardsFab = (FloatingActionButton) findViewById(R.id.vacab_act_fab);
        runCardsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO start card learning
            }
        });


        confirmPackRemove = new MaterialDialog.Builder(this)
                .content(R.string.vocab_act_pack_remove_confirm_question)
                .positiveText(R.string.vocab_act_pack_remove_confirm_btn)
                .negativeText(R.string.vocab_act_pack_remove_cancel_btn)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        packsListAdapter.removePacks(packsListAdapter.getSelectedItems());
                        packsListAdapter.clearSelection();
                        confirmPackRemove.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        packsListAdapter.clearSelection();
                        confirmPackRemove.dismiss();
                    }
                })
                .build();

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
                //TODO save packs on storage
                onBackPressed();
                return true;

            case R.id.vocab_act_action_add_pack:
                packsListAdapter.addPack();
                return true;

            case R.id.vocab_act_action_cloud_download:
                // TODO
                return true;

            case R.id.vocab_act_action_remove_pack:

                if (packsListAdapter.getSelectedItemCount() > 0) {
                    confirmPackRemove.show();
                }
                if (!runCardsFab.isHidden()) {
                    runCardsFab.hide(true);
                }
                return true;

            case R.id.vocab_act_action_edit_pack:

                //TODO
                startActivity(new Intent(VocabularyActivity.this, EditPackActivity.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(int position) {
        //TODO aggregate cards to learn here perhaps
        packsListAdapter.toggleSelection(position);

        if (packsListAdapter.getSelectedItemCount() > 0) {
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
