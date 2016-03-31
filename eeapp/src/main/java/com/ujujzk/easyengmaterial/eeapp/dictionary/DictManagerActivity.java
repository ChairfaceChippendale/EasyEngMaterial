package com.ujujzk.easyengmaterial.eeapp.dictionary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import com.ujujzk.easyengmaterial.eeapp.model.Word;
import com.ujujzk.easyengmaterial.eeapp.model.Article;
import com.ujujzk.easyengmaterial.eeapp.service.DictInstallService;
import com.ujujzk.easyengmaterial.eeapp.service.DictRemoveService;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;


public class DictManagerActivity extends AppCompatActivity implements DictionaryListAdapter.DictionaryViewHolder.ClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = DictManagerActivity.class.getSimpleName();
    public static final String DICTIONARY_IN_PROCESS = "dictionaryInProcess";
    public static final String DICT_INSTALL_SERVICE_STATUS = "dictInstallServiceStatus";

    private Toolbar toolBar;
    private FloatingActionButton installNewDictionariesFab;
    private RecyclerView dictionaryList;
    private LinearLayoutManager dictionaryListManager;
    private DictionaryListAdapter dictionaryListAdapter;
    private MaterialDialog confirmDictionaryRemove;
    private LinearLayout progressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic_manager);

        toolBar = (Toolbar) findViewById(R.id.dict_manager_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dictionaryList = (RecyclerView) findViewById(R.id.dict_manager_act_rv_dic_list);
        dictionaryList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        dictionaryListManager = new LinearLayoutManager(this);
        dictionaryList.setLayoutManager(dictionaryListManager);

        dictionaryListAdapter = new DictionaryListAdapter(this, this);
        dictionaryList.setAdapter(dictionaryListAdapter);
        dictionaryList.setItemAnimator(new DefaultItemAnimator());

        progressView = (LinearLayout) findViewById(R.id.dict_manager_act_progress_view);

        installNewDictionariesFab = (FloatingActionButton) findViewById(R.id.dict_manager_act_fab);
        installNewDictionariesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installNewDictionaries();
            }
        });

        makeDictInstallBroadcastReceiver ();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {
        confirmDictionaryRemove = getDictionaryRemoveDialog(position);
        confirmDictionaryRemove.show();
    }

    private void makeDictInstallBroadcastReceiver () {
        BroadcastReceiver dictInstallBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String dictName = intent.getStringExtra(DICTIONARY_IN_PROCESS);
                dictionaryListAdapter.setDictInProcess(dictName);
                dictionaryListAdapter.notifyDataSetChanged();
                Log.d(TAG, "" + dictName);
            }
        };
        registerReceiver(dictInstallBroadcastReceiver, new IntentFilter(DICT_INSTALL_SERVICE_STATUS) );
    }

    private MaterialDialog getDictionaryRemoveDialog(final int position) {
        final int dictPosition = position;
        final String dictName = dictionaryListAdapter.getDictionary(dictPosition).getDictionaryName();
        return new MaterialDialog.Builder(this)
                .content(getResources().getString(R.string.dict_manager_act_dictionary_remove_confirm_question) + " " + dictName + "?")
                .positiveText(R.string.dict_manager_act_dictionary_remove_confirm_btn)
                .negativeText(R.string.dict_manager_act_dictionary_remove_cancel_btn)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        removeDictionary(dictPosition);
                        confirmDictionaryRemove.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        confirmDictionaryRemove.dismiss();
                    }
                })
                .build();
    }

    private void removeDictionary(int dictPosition) {

        Long dictToRemoveId = dictionaryListAdapter.getDictionary(dictPosition).getLocalId();


        Intent intent = new Intent(DictManagerActivity.this, DictRemoveService.class);
        intent.putExtra(DictRemoveService.DICT_TO_REMOVE_ID, dictToRemoveId.longValue());
        startService(intent);

        dictionaryListAdapter.removeDictionary(dictPosition);
    }

    private void installNewDictionaries() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Snackbar.make(installNewDictionariesFab, "SD-card is not accessible", Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "SD-card is not accessible");
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        path = new File(path.getAbsolutePath() + "/" + getResources().getString(R.string.app_name));

        if (!path.exists()) {
            path.mkdirs();
            Snackbar.make(installNewDictionariesFab, "There are no new dictionaries.", Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "There were no new dictionaries, a folder for dictionaries was mode");
            return;
        }

        List<File> dslFiles = getDictFiles(path);

        if (dslFiles.isEmpty()) {
            Snackbar.make(installNewDictionariesFab, "There are no new dictionaries.", Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "There are no new dictionaries in the Folder on ExternalStorage");
            return;
        }

        addAllDictToDataBase(dslFiles);
    }

    private List<File> getDictFiles (File path) {

        List<File> allFiles = Arrays.asList(path.listFiles());
        List<File> dslFiles = new ArrayList<File>();

        for (File f : allFiles) {
            final String fileName = f.getName();

            if (fileName.endsWith(".dsl")) {
                if (!fileName.endsWith("_abrv.dsl")) { //special files with abbreviations
                    dslFiles.add(f);
                }
            }
        }

        return dslFiles;
    }

    private void addAllDictToDataBase (List<File> dictFiles) {

        ArrayList<String> dictFilePaths = new ArrayList<String>();
        for (File dictFile: dictFiles) {
            dictFilePaths.add(dictFile.getAbsolutePath());
        }

        Intent intent = new Intent(DictManagerActivity.this, DictInstallService.class);
        intent.putStringArrayListExtra(DictInstallService.DICT_FILE_PATHS, dictFilePaths);
        startService(intent);

        //dictionaryList.setVisibility(View.GONE);
        installNewDictionariesFab.hide(true);
        //progressView.setVisibility(View.VISIBLE);
    }
}
