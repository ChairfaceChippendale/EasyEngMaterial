package com.ujujzk.easyengmaterial.eeapp.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.dictionary.DictManagerActivity;
import com.ujujzk.easyengmaterial.eeapp.model.Article;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import com.ujujzk.easyengmaterial.eeapp.model.Word;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictInstallService extends Service {

    private static final String TAG = DictInstallService.class.getSimpleName();

    private static final String DICTIONARY_NAME_TAG_IN_FILE = "#NAME";
    private static final int DICTIONARY_NAME_SEARCHING_ROW_NUMBER = 10;
    private static final int BUFFER_SIZE = 1000;//5000 - is too much

    private static boolean isRun;

    public static final String DICT_FILE_PATHS = "dictFilesToInstall";

    private Thread installDictsThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRun = true;

        List<String> dictFilePaths = intent.getStringArrayListExtra(DICT_FILE_PATHS);
        addAllDictToDataBase(dictFilePaths);

        return super.onStartCommand(intent, flags, startId);
    }

    public static boolean isRun() {
        return isRun;
    }

    private void addAllDictToDataBase (final List<String> dictFilePaths) {

        installDictsThread = new Thread(new Runnable() {
            @Override
            public void run() {

                List<File> dictFiles = new ArrayList<File>();

                if (dictFilePaths != null && !dictFilePaths.isEmpty()) {
                    for (String filePath : dictFilePaths) {
                        dictFiles.add(new File(filePath));
                    }
                }

                if (!dictFiles.isEmpty()) {

                    for (File file : dictFiles) {
                        if (file.getName().endsWith(".dsl")) {
                            addDictToDataBase(file);
                        }
                    }
                }
                isRun = false;
                stopSelf();
            }
        });
        installDictsThread.start();

    }

    private void addDictToDataBase(File file) {

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
            return;
        }

        List<Dictionary> existedDictionariesWithCurrentName = Application.localStore.readBy(Dictionary.class, new KeyValue("dictionaryName", dictionaryName));
        if (!existedDictionariesWithCurrentName.isEmpty()) {
            Log.d(TAG, "Dictionary " + dictionaryName + " already exists.");
            return;
        }

        Dictionary newDictionary = Application.localStore.create(new Dictionary(dictionaryName));
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
                        saveWordsAndArticles(wordBuffer, rawArticleBuffer, dictionaryName);
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
                saveWordsAndArticles(wordBuffer, rawArticleBuffer, "no");
                wordBuffer.clear();
                rawArticleBuffer.clear();
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveWordsAndArticles(ArrayList<Word> wordBuffer, ArrayList<Article> rawArticleBuffer, String dictName) {
        Application.localStore.createFast(wordBuffer, Word.class);
        Application.localStore.createFast(rawArticleBuffer, Article.class);
        sendBroadcastToDictManagerActivity(dictName);
    }

    private void sendBroadcastToDictManagerActivity(String dictName) {
        Intent intent = new Intent(DictManagerActivity.DICT_INSTALL_SERVICE_STATUS);
        intent.putExtra(DictManagerActivity.DICTIONARY_IN_PROCESS, dictName);
        sendBroadcast(intent);
    }

    private String getWordNameFromRawArticle(String rawArticle) {
        return rawArticle.substring(0, rawArticle.indexOf("\t"))
                .trim()
                .replaceAll("\\{[^\\{]*\\}", "") //remove stress tags from word
                .replaceAll("[{}()]", ""); //it removes brackets {} () from word
    }
}
