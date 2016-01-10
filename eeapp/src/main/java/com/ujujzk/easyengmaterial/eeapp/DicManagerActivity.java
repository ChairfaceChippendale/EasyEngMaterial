package com.ujujzk.easyengmaterial.eeapp;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
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
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.model.Card;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import com.ujujzk.easyengmaterial.eeapp.model.Word;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class DicManagerActivity extends AppCompatActivity implements DictionaryListAdapter.DictionaryViewHolder.ClickListener{

    @SuppressWarnings("unused")
    private static final String TAG = DicManagerActivity.class.getSimpleName();

    private static final String DICTIONARY_NAME_TAG_IN_FILE = "#NAME";
    private static final int DICTIONARY_NAME_SEARCHING_ROW_NUMBER = 10;

    private Toolbar toolBar;
    private FloatingActionButton initializeNewDictionariesFab;
    private RecyclerView dictionaryList;
    private LinearLayoutManager dictionaryListManager;
    private DictionaryListAdapter dictionaryListAdapter;
    private List<Dictionary> mDictionaries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic_manager);

        toolBar = (Toolbar) findViewById(R.id.dic_manager_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDictionaries = Application.localStore.readAll(Dictionary.class);

        dictionaryList = (RecyclerView) findViewById(R.id.dic_manager_act_rv_dic_list);
        dictionaryList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        dictionaryListManager = new LinearLayoutManager(this);
        dictionaryList.setLayoutManager(dictionaryListManager);

        dictionaryListAdapter = new DictionaryListAdapter(mDictionaries, this);
        dictionaryList.setAdapter(dictionaryListAdapter);
        dictionaryList.setItemAnimator(new DefaultItemAnimator());


        initializeNewDictionariesFab = (FloatingActionButton) findViewById(R.id.dic_manager_act_fab);
        initializeNewDictionariesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO load new dictionaries from folder?

                initializeNewDictionaries();

            }
        });
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
        //TODO
    }

    private void initializeNewDictionaries() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //TODO Massage that SD-card is not accessible
            Log.d(TAG, "SD-card is not accessible");
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        path = new File(path.getAbsolutePath() + "/" + getResources().getString(R.string.app_name));

        if (!path.exists()) {
            path.mkdirs();
            //TODO Massage that there aren't new dictionaries
            Log.d(TAG, "There were no new dictionaries, a folder for dictionaries was mode");
            return;
        }

        List<File> allFiles = Arrays.asList(path.listFiles());
        List<File> dslFiles = new ArrayList<File>();

        for (File f : allFiles) {
            if (f.getName().endsWith(".dsl")) {
                dslFiles.add(f);
            }
        }

        if (dslFiles.isEmpty()) {
            //TODO Massage that there aren't new dictionaries. Notice that dictionary must have expansion .dsl
            Log.d(TAG, "There were no new dictionaries");
            return;
        }

        for (File file : dslFiles) {
            if (file.getName().endsWith(".dsl")) {
                addDictionaryToDataBase(file);
            }
        }


    }

    private void addDictionaryToDataBase(File file) {

        String newDictionaryName = "";


        try {
            //BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            Scanner scanner = new Scanner(file, Charset.forName("UTF-16").name());
            String line;

            for (int i = 0; i < DICTIONARY_NAME_SEARCHING_ROW_NUMBER && scanner.hasNext(); i++) {
                line = scanner.nextLine();
                line = line.trim();
                Log.d(TAG, "Line " + i + " is " + line);
                if (line.startsWith(DICTIONARY_NAME_TAG_IN_FILE)) {
                    newDictionaryName = line;
                    newDictionaryName = newDictionaryName.replace(DICTIONARY_NAME_TAG_IN_FILE, "");
                    newDictionaryName = newDictionaryName.replace("\"", "");
                    newDictionaryName = newDictionaryName.trim();
                    Log.d(TAG, "New dictionary name is " + newDictionaryName);
                    break;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (newDictionaryName.isEmpty()) {
            Log.d(TAG, "File " + file.getAbsolutePath() + " doesn't have dictionary name.");
            return;
        }

            //TODO doesn't work still
//        List<Dictionary> existedDictionariesWithCurrentName = Application.localStore.readBy(Dictionary.class, new KeyValue("dictionaryName", newDictionaryName));
//        if (!existedDictionariesWithCurrentName.isEmpty()){
//            Log.d(TAG, "Dictionary " + newDictionaryName + " already exists.");
//            return;
//        }
        //Temporary solution
        List<Dictionary> allDictionariesInBase = mDictionaries;
        for (Dictionary d: allDictionariesInBase){
            if (d.getDictionaryName().equals(newDictionaryName)){
                Log.d(TAG, "Dictionary " + newDictionaryName + " already exists.");
                return;
            }
        }


        Dictionary newDictionary = Application.localStore.create(new Dictionary(newDictionaryName));
        dictionaryListAdapter.addDictionary(newDictionary);
        Long newDictionaryId = newDictionary.getLocalId();


        //temp-----------------
        List<String> wordStrings = new ArrayList<String>();
        //---------------

        try {
            Scanner scanner = new Scanner(file, Charset.forName("UTF-16").name());
            String line;
            StringBuilder wordStr = new StringBuilder("");

            while (scanner.hasNext()) {
                line = scanner.nextLine();

                if (line.startsWith("#")) {
                    continue;
                }
                if (line.equals("\t")) {
                    wordStrings.add(wordStr.toString());
                    wordStr.setLength(0);
                } else {
                    wordStr.append(line);
                }

            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String s: wordStrings){
            Log.d(TAG, s);
        }


        //TODO make Word and WordArticle from wordStrings
    }

}
