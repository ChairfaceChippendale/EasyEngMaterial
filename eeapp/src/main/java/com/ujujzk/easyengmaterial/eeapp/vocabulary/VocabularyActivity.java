package com.ujujzk.easyengmaterial.eeapp.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.ujujzk.easyengmaterial.eeapp.*;
import com.ujujzk.easyengmaterial.eeapp.dictionary.DictionaryActivity;
import com.ujujzk.easyengmaterial.eeapp.grammar.GrammarActivity;
import com.ujujzk.ee.domain.usecase.voc.model.Card;
import com.ujujzk.ee.domain.usecase.voc.model.Pack;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class VocabularyActivity extends AppCompatActivity implements PacksListAdapter.PackViewHolder.ClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = VocabularyActivity.class.getSimpleName();

    private static final int GRIDS_ON_TABLET = 2;
    private static final int GRIDS_ON_PHONE = 1;

    static final String SELECTED_PACK_ID = "selectedPackId";
    static final String SELECTED_CARD_IDS = "selectedCardIds";

    private Toolbar toolBar;
    private Drawer navigationDrawer = null;
    private RecyclerView packList;
    private PacksListAdapter packListAdapter;
    private CircularProgressView progressBar;
    private FloatingActionButton runCardsFab;
    private MaterialDialog confirmPackRemove;
    private Button addPackBtn;
    private View withPacksView;
    private View withoutPacksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        toolBar = (Toolbar) findViewById(R.id.vocab_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);

        navigationDrawer = makeNavigationDrawer();
        navigationDrawer.setSelection(Application.IDENTIFIER_VOCABULARY);

        withPacksView = findViewById(R.id.vocab_act_full_data);
        withoutPacksView = findViewById(R.id.vocab_act_empty_data);

        progressBar = (CircularProgressView) findViewById(R.id.vocab_act_progress_bar);

        addPackBtn = (Button) findViewById(R.id.vocab_act_empty_data_btn);
        addPackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPack();
            }
        });

        packList = (RecyclerView) findViewById(R.id.vocab_act_rv_packs_list);
        packListAdapter = new PacksListAdapter(this);
        packList.setAdapter(packListAdapter);
        StaggeredGridLayoutManager packsGridLayoutManager;
        if (isTablet(this) || isLandScape(this)) {
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
                    intent.putExtra(SELECTED_CARD_IDS, (ArrayList<Long>) ids);
                    startActivity(intent);
                    overridePendingTransition(R.animator.activity_appear_from_right, R.animator.activity_disappear_alpha); //custom activity transition animation
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
                        emptyStateTrigger(packListAdapter.isEmpty());
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

    private Drawer makeNavigationDrawer () {
        return new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolBar)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(
                        new AccountHeaderBuilder()
                                .withActivity(this)
                                .withHeaderBackground(R.drawable.img_vocab)
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
                                .withIdentifier(Application.IDENTIFIER_FEEDBACK)
//                        new SecondaryDrawerItem()
//                                .withName(R.string.title_activity_settings)
//                                .withIcon(GoogleMaterial.Icon.gmd_settings)
//                                .withIdentifier(Application.IDENTIFIER_SETTING)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch((int)drawerItem.getIdentifier()){
                            case Application.IDENTIFIER_DICTIONARY:
                                startActivity(new Intent(VocabularyActivity.this, DictionaryActivity.class));
                                finish();
                                break;
                            case Application.IDENTIFIER_VOCABULARY:
                                break;
                            case Application.IDENTIFIER_GRAMMAR:
                                startActivity(new Intent(VocabularyActivity.this, GrammarActivity.class));
                                finish();
                                break;
                            case Application.IDENTIFIER_ABOUT:
                                startActivity(new Intent(VocabularyActivity.this, AboutActivity.class));
                                break;
                            case Application.IDENTIFIER_SHARE:
                                sendSharingMassage(getResources().getString(R.string.sharing_massage));
                                break;
                            case Application.IDENTIFIER_FEEDBACK:
                                sendFeedBack("I like this app");
                                break;
                            case Application.IDENTIFIER_SETTING:
                                startActivity(new Intent(VocabularyActivity.this, SettingsActivity.class));
                                break;
                            default:
                                break;
                        }
                        navigationDrawer.closeDrawer();
                        return true;
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        if(navigationDrawer.isDrawerOpen()){
            navigationDrawer.closeDrawer();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        new AsyncTask<Void, Void, List<Pack>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                packList.setVisibility(View.GONE);
                emptyStateTrigger(false);
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
                emptyStateTrigger(packListAdapter.isEmpty());
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

            case R.id.vocab_act_action_add_pack:
                addNewPack();
                return true;

            case R.id.vocab_act_action_cloud_download:
                downloadPacksFromCloud();
                return true;
//
//            case R.id.vocab_act_action_folder_download:
//                //TODO
//                return true;

            case R.id.vocab_act_action_remove_pack:
                removeSelectedPacks();
                return true;

            case R.id.vocab_act_action_edit_pack:
                runEditPack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNewPack () {
        packListAdapter.clearSelection();
        packListAdapter.addPackOnPosition(0,
                Application.localStore.create(new Pack("New pack", new ArrayList<Card>()))
        );
        emptyStateTrigger(false);
    }

    private void downloadPacksFromCloud () {
        if (isNetworkConnected()) {
            new AsyncTask<Void, Void, List<Pack>>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    packList.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    emptyStateTrigger(false);
                }

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
                    packList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    emptyStateTrigger(packListAdapter.isEmpty());
                }
            }.execute();
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private void removeSelectedPacks () {
        if (packListAdapter.getSelectedItemCount() > 0) {
            confirmPackRemove.show();
        }
        if (!runCardsFab.isHidden()) {
            runCardsFab.hide(true);
        }
        emptyStateTrigger(packListAdapter.isEmpty());
    }

    private void runEditPack () {
        if (packListAdapter.getSelectedItemCount() == 1) {

            List<Long> ids = packListAdapter.getSelectedPacksId(packListAdapter.getSelectedItems());
            if (ids.size() > 0) {

                Intent intent = new Intent(VocabularyActivity.this, EditPackActivity.class);
                intent.putExtra(SELECTED_PACK_ID, ids.get(0));
                startActivity(intent);
                overridePendingTransition(R.animator.activity_appear_from_right, R.animator.activity_disappear_alpha); //custom activity transition animation

            }
        }
        runCardsFab.hide(true);
        packListAdapter.clearSelection();
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
        return true;
    }

    private static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private static boolean isLandScape (Context context){
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
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

    private void sendFeedBack(String massage){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("text/email");
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { getResources().getString(R.string.feed_back_email) });
        email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feed_back_subject));
        email.putExtra(Intent.EXTRA_TEXT, massage);
        startActivity(Intent.createChooser(email, getResources().getString(R.string.feed_back_title)));
    }

    private void emptyStateTrigger (boolean switchOnEmptyState) {
        if (switchOnEmptyState) {
            withoutPacksView.setVisibility(View.VISIBLE);
            withPacksView.setVisibility(View.GONE);
        } else {
            withPacksView.setVisibility(View.VISIBLE);
            withoutPacksView.setVisibility(View.GONE);
        }
    }

}
