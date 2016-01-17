package com.ujujzk.easyengmaterial.eeapp;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import com.ujujzk.easyengmaterial.eeapp.model.Word;
import com.ujujzk.easyengmaterial.eeapp.model.WordArticle;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class DictManagerActivity extends AppCompatActivity implements DictionaryListAdapter.DictionaryViewHolder.ClickListener{

    @SuppressWarnings("unused")
    private static final String TAG = DictManagerActivity.class.getSimpleName();

    private static final String DICTIONARY_NAME_TAG_IN_FILE = "#NAME";
    private static final int DICTIONARY_NAME_SEARCHING_ROW_NUMBER = 10;

    private Toolbar toolBar;
    private FloatingActionButton initializeNewDictionariesFab;
    private RecyclerView dictionaryList;
    private LinearLayoutManager dictionaryListManager;
    private DictionaryListAdapter dictionaryListAdapter;
    private MaterialDialog confirmDictionaryRemove;

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

        dictionaryList = (RecyclerView) findViewById(R.id.dic_manager_act_rv_dic_list);
        dictionaryList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        dictionaryListManager = new LinearLayoutManager(this);
        dictionaryList.setLayoutManager(dictionaryListManager);

        dictionaryListAdapter = new DictionaryListAdapter(Application.localStore.readAll(Dictionary.class), this, this);
        dictionaryList.setAdapter(dictionaryListAdapter);
        dictionaryList.setItemAnimator(new DefaultItemAnimator());


        initializeNewDictionariesFab = (FloatingActionButton) findViewById(R.id.dic_manager_act_fab);
        initializeNewDictionariesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        confirmDictionaryRemove = getDictionaryRemoveDialog(position);
        confirmDictionaryRemove.show();
    }

    private MaterialDialog getDictionaryRemoveDialog (final int position){
        final int dictPosition = position;
        final String dictName = dictionaryListAdapter.getDictionary(dictPosition).getDictionaryName();
        return new MaterialDialog.Builder(this)
                .content(getResources().getString(R.string.manager_act_dictionary_remove_confirm_question) + " " + dictName + "?")
                .positiveText(R.string.manager_act_dictionary_remove_confirm_btn)
                .negativeText(R.string.manager_act_dictionary_remove_cancel_btn)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        removeDictionary(dictPosition, dictName);
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

    private void removeDictionary (int dictPosition, String dictName) {
        Long dictLocalId = dictionaryListAdapter.getDictionary(dictPosition).getLocalId();

        //TODO remove all articles related to this dictionary - CHECK
        List<WordArticle> articles = Application.localStore.readBy(WordArticle.class, new KeyValue("dictionaryId", dictLocalId));
        if (!articles.isEmpty()){
            for (WordArticle article: articles){
                Application.localStore.delete(article.getLocalId(), WordArticle.class);
            }
        }
        //TODO remove all words that don't have articles - CHECK
        List<Word> words = Application.localStore.readAll(Word.class);
        for (Word word: words){
            List<WordArticle> articleList = Application.localStore.readBy(WordArticle.class, new KeyValue("wordId", word.getLocalId()));
            if (articleList.isEmpty()){
                Application.localStore.delete(word.getLocalId(),Word.class);
            }
        }

        Application.localStore.delete(dictLocalId, Dictionary.class);
        dictionaryListAdapter.removeDictionary(dictPosition);
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
                    Log.d(TAG, "Name of new dictionary is " + newDictionaryName);
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

        List<Dictionary> existedDictionariesWithCurrentName = Application.localStore.readBy(Dictionary.class, new KeyValue("dictionaryName", newDictionaryName));
        if (!existedDictionariesWithCurrentName.isEmpty()){
            Log.d(TAG, "Dictionary " + newDictionaryName + " already exists.");
            return;
        }

        Dictionary newDictionary = Application.localStore.create(new Dictionary(newDictionaryName));
        dictionaryListAdapter.addDictionary(newDictionary);
        Long newDictionaryLocalId = newDictionary.getLocalId();


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
                    saveWordAndArticle(wordStr.toString(),newDictionaryLocalId);
                    wordStr.setLength(0);
                } else {
                    wordStr.append(line);
                }

            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveWordAndArticle (String string, Long dictLocalId){

        final Pair<String,String> wordAndArticle = stringToWordAndArticle(string);

        if (wordAndArticle != null){

            final String wordName = wordAndArticle.first;
            final String article = wordAndArticle.second;

            List<Word> existingWords = Application.localStore.readBy(Word.class, new KeyValue("wordName",wordName));
            Long wordLocalId;
            if (existingWords.isEmpty()){
                wordLocalId = (Application.localStore.create(new Word(wordName))).getLocalId();
            } else {
                wordLocalId = existingWords.get(0).getLocalId();
            }

            Application.localStore.create(new WordArticle(article, wordLocalId, dictLocalId));

        }
    }

    private Pair<String,String> stringToWordAndArticle(String str){

        String word;
        String article;

        if (str.contains("[sub]")) {
            return null;
        }

        str = str.replaceAll("\\[/?(\\*|!trs|'|trn|com)\\]", "");
        str = str.replace("{", "");
        str = str.replace("}", "");
        str = str.replaceAll("\\[(s|url)\\][^\\[]*\\[/(s|url)\\]", "");
        str = str.replaceAll("\\[/?lang[^\\[]*\\]", "");

        str = str.replaceAll("\\[c\\](\\[com\\])?", "<span style='color:green'>");
        str = str.replaceAll("(\\[/com\\])?\\[/c\\]", "</span>");
        str = str.replace("[c]", "<span style='color:green'>");
        str = str.replace("[p]", "<span style='color:green'><i>");
        str = str.replace("[/p]", "</i></span>");
        str = str.replaceAll("\\[(ex|c gray)\\]", "<span style='color:gray'>");
        str = str.replaceAll("\\[/(ex|com)\\]", "</span>");
        str = str.replace("[ref]", "<span style='color:blue'>");
        str = str.replace("[/ref]", "</span>");

        str = str.replaceAll("\\[m[1-9]?\\]", "");
        str = str.replace("[/m]", "<br>");

        str = str.replace("[b]", "<b>");
        str = str.replace("[i]", "<i>");
        str = str.replace("[u]", "<u>");
        str = str.replace("[/b]", "</b>");
        str = str.replace("[/i]", "</i>");
        str = str.replace("[/u]", "</u>");

        str = str.replace("\\[", "[");
        str = str.replace("\\]", "]");

        str = str.replaceAll("\\[\\[t\\][^\\[]*\\[/t\\]\\]", "");

        word = str.substring(0,str.indexOf("\t"));
        article = str.substring(str.indexOf("\t")+1);

        if (word.isEmpty() || article.isEmpty()){
            return null;
        }

        return new Pair<String, String>(word, article);
    }

}
