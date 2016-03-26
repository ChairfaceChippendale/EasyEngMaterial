package com.ujujzk.easyengmaterial.eeapp.service;

//word pronunciation
//powered by Google
//https://ssl.gstatic.com/dictionary/static/sounds/de/0/WORD.mp3

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;


public class PronunciationService extends Service implements MediaPlayer.OnPreparedListener {

    private final String AUDIO_PLAYER_LOG = "Audio Player Log";
    public final static String PRONUNCIATION_TASK = "com.ujujzk.easyenglish.eeapp.playerTask";
    public final static String WORD = "wordtopronounce";
    private final static String SRC_START = "https://ssl.gstatic.com/dictionary/static/sounds/de/0/";
    private final static String SRC_END = ".mp3";


    static MediaPlayer mediaPlayer;

    BroadcastReceiver br;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(AUDIO_PLAYER_LOG, "Audio Player was created");

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String trackSource = SRC_START + intent.getStringExtra(WORD) + SRC_END;
                play(trackSource);

            }
        };
        IntentFilter intFilt = new IntentFilter(PRONUNCIATION_TASK);
        registerReceiver(br, intFilt);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(AUDIO_PLAYER_LOG, "Audio Player was started");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(br);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d(AUDIO_PLAYER_LOG, "media player was stopped");
        }

        Log.d(AUDIO_PLAYER_LOG, "Audio Player was destroyed");
        stopSelf();
    }


    private void play (String trackSrc) {

        try {

            if (mediaPlayer != null) {

                if (mediaPlayer.isPlaying()) {

                    mediaPlayer.stop();
                    Log.d(AUDIO_PLAYER_LOG, "media player was stopped");
                    mediaPlayer.release();
                    mediaPlayer = null;
                    return;

                }

                mediaPlayer.release();
                mediaPlayer = null;

            }

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(trackSrc);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
}
