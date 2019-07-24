package com.ujujzk.easyengmaterial.eeapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.ujujzk.ee.domain.usecase.dic.model.Article;
import com.ujujzk.ee.domain.usecase.dic.model.Word;

public class DictRemoveService extends Service {

    private static final String TAG = DictRemoveService.class.getSimpleName();

    public static final String DICT_TO_REMOVE_ID = "dictToRemoveId";

    private Thread removeDictThread;

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
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        Long dictToDeleteId = intent.getLongExtra(DICT_TO_REMOVE_ID, 0);
        if (dictToDeleteId > 0){
            removeDictionary(dictToDeleteId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void removeDictionary(final Long dictToDeleteId) {

        removeDictThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Application.localStore.delete(dictToDeleteId, Dictionary.class);
                Application.localStore.deleteBy(Article.class, new KeyValue("dictionaryId", dictToDeleteId));
                Application.localStore.deleteBy(Word.class, new KeyValue("dictionaryId", dictToDeleteId));

                stopSelf();
            }
        });
        removeDictThread.start();
    }
}
