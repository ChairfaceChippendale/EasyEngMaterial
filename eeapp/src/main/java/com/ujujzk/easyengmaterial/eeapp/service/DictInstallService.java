package com.ujujzk.easyengmaterial.eeapp.service;


import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.util.Log;
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.dictionary.DictManagerActivity;
import com.ujujzk.easyengmaterial.eeapp.model.Article;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import com.ujujzk.easyengmaterial.eeapp.model.Word;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DictInstallService extends Service{

    static final String TAG = DictInstallService.class.getSimpleName();

    private static final String DICTIONARY_NAME_TAG_IN_FILE = "#NAME";
    private static final int DICTIONARY_NAME_SEARCHING_ROW_NUMBER = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }










    private void saveWordsAndArticles(ArrayList<Word> wordBuffer, ArrayList<Article> rawArticleBuffer) {
        Application.localStore.createFast(wordBuffer, Word.class);
        Application.localStore.createFast(rawArticleBuffer, Article.class);
    }

    private String getWordNameFromRawArticle(String rawArticle) {
        return rawArticle.substring(0, rawArticle.indexOf("\t")).trim();
    }
}
