package com.ujujzk.easyengmaterial.eeapp.dictionary;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import com.ujujzk.easyengmaterial.eeapp.model.Word;
import com.ujujzk.easyengmaterial.eeapp.model.Article;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;


public class DictManagerActivity extends AppCompatActivity implements DictionaryListAdapter.DictionaryViewHolder.ClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = DictManagerActivity.class.getSimpleName();
    public static final String DATA_CHANGED = "data_changed";

    private static final String DICTIONARY_NAME_TAG_IN_FILE = "#NAME";
    private static final int DICTIONARY_NAME_SEARCHING_ROW_NUMBER = 10;
    private static final int BUFFER_SIZE = 1000;//5000 - is too much

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

    private MaterialDialog getDictionaryRemoveDialog(final int position) {
        final int dictPosition = position;
        final String dictName = dictionaryListAdapter.getDictionary(dictPosition).getDictionaryName();
        return new MaterialDialog.Builder(this)
                .content(getResources().getString(R.string.manager_act_dictionary_remove_confirm_question) + " " + dictName + "?")
                .positiveText(R.string.manager_act_dictionary_remove_confirm_btn)
                .negativeText(R.string.manager_act_dictionary_remove_cancel_btn)
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
        //TODO revise all method
        //TODO method deleteBy() is needed

        Long dictToDeleteId = dictionaryListAdapter.getDictionary(dictPosition).getLocalId();

        List<Article> articlesToDelete = Application.localStore.readBy(Article.class, new KeyValue("dictionaryId", dictToDeleteId));
        if (!articlesToDelete.isEmpty()) {
            for (Article article : articlesToDelete) {
                Application.localStore.delete(article.getLocalId(), Article.class);
            }
        }

        List<Word> wordsToDelete = Application.localStore.readBy(Word.class, new KeyValue("dictionaryId", dictToDeleteId));
        if (!wordsToDelete.isEmpty()){
            for (Word word : wordsToDelete) {
                Application.localStore.delete(word.getLocalId(), Word.class);
            }
        }

        Application.localStore.delete(dictToDeleteId, Dictionary.class);
        dictionaryListAdapter.removeDictionary(dictPosition);

    }

    private void initializeNewDictionaries() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Snackbar.make(initializeNewDictionariesFab, "SD-card is not accessible", Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "SD-card is not accessible");
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        path = new File(path.getAbsolutePath() + "/" + getResources().getString(R.string.app_name));

        if (!path.exists()) {
            path.mkdirs();
            Snackbar.make(initializeNewDictionariesFab, "There are no new dictionaries.", Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "There were no new dictionaries, a folder for dictionaries was mode");
            return;
        }

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

        if (dslFiles.isEmpty()) {
            //TODO Massage that there aren't new dictionaries. Notice that dictionary must have expansion .dsl
            Snackbar.make(initializeNewDictionariesFab, "There are no new dictionaries.", Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "There are no new dictionaries in the Folder on ExternalStorage");
            return;
        }

        for (File file : dslFiles) {
            if (file.getName().endsWith(".dsl")) {
                addDictionaryToDataBase(file);
            }
        }
    }

    private void addDictionaryToDataBase(File file) {

        String dictionaryName = "";

        try {
            Scanner scanner = new Scanner(file, Charset.forName("UTF-16").name());
            String line;

            for (int i = 0; i < DICTIONARY_NAME_SEARCHING_ROW_NUMBER && scanner.hasNext(); i++) {
                line = scanner.nextLine();
                line = line.trim();
                Log.d(TAG, "Line " + i + " is " + line);
                if (line.startsWith(DICTIONARY_NAME_TAG_IN_FILE)) {
                    dictionaryName = line;
                    dictionaryName = dictionaryName.replace(DICTIONARY_NAME_TAG_IN_FILE, "");
                    dictionaryName = dictionaryName.replace("\"", "");
                    dictionaryName = dictionaryName.trim();
                    Log.d(TAG, "Name of new dictionary is " + dictionaryName);
                    break;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (dictionaryName.isEmpty()) {
            Log.d(TAG, "File " + file.getAbsolutePath() + " doesn't have dictionary name.");
            Snackbar.make(initializeNewDictionariesFab, "File " + file.getName() + " doesn't contain dictionary name.", Snackbar.LENGTH_LONG).show();
            return;
        }

        List<Dictionary> existedDictionariesWithCurrentName = Application.localStore.readBy(Dictionary.class, new KeyValue("dictionaryName", dictionaryName));
        if (!existedDictionariesWithCurrentName.isEmpty()) {
            Log.d(TAG, "Dictionary " + dictionaryName + " already exists.");
            return;
        }

        Dictionary newDictionary = Application.localStore.create(new Dictionary(dictionaryName));
        dictionaryListAdapter.addDictionary(newDictionary);
        Long dictId = newDictionary.getLocalId();

        try {
            Scanner scanner = new Scanner(file, Charset.forName("UTF-16").name());

            ArrayList<Word> wordBuffer = new ArrayList<Word>();
            ArrayList<Article> rawArticleBuffer = new ArrayList<Article>();
            String line;
            StringBuilder rawArticle = new StringBuilder("");

            while (scanner.hasNext()) {
                line = scanner.nextLine();

                if (line.startsWith("#")) {//rows with dictionary name and languages
                    continue;
                }
                if (line.equals("\t")) {

                    String wordName = getWordNameFromRawArticle(rawArticle.toString());

                    if (wordName.length() > 0) {
                        wordBuffer.add( new Word (wordName, dictId) );
                        rawArticleBuffer.add( new Article (rawArticle.toString(), wordName, dictId) );
                    }
                    if (wordBuffer.size() == BUFFER_SIZE) {
                        saveWordsAndArticles(wordBuffer, rawArticleBuffer);
                        wordBuffer.clear();
                        rawArticleBuffer.clear();
                    }
                    rawArticle.setLength(0);
                } else {
                    rawArticle.append(line);
                    rawArticle.append("\n");
                }
            }

            if (!wordBuffer.isEmpty()) {
                saveWordsAndArticles(wordBuffer, rawArticleBuffer);
                wordBuffer.clear();
                rawArticleBuffer.clear();
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveWordsAndArticles(ArrayList<Word> wordBuffer, ArrayList<Article> rawArticleBuffer) {
            Application.localStore.createFast(wordBuffer, Word.class);
            Application.localStore.createFast(rawArticleBuffer, Article.class);
    }

    private String getWordNameFromRawArticle(String rawArticle) {
        return rawArticle.substring(0, rawArticle.indexOf("\t")).trim();
    }

}
