package com.ujujzk.easyengmaterial.eeapp.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Card;
import com.ujujzk.easyengmaterial.eeapp.service.PronunciationService;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearnWordActivity extends AppCompatActivity implements View.OnTouchListener{

    @SuppressWarnings("unused")
    private static final String TAG = LearnWordActivity.class.getSimpleName();

    private static final int WORD_VIEW_MOVE_DURATION = 400;

    private static final int MOVE_UP = 320001;
    private static final int MOVE_DOWN = 320002;
    private static final int MOVE_RIGHT = 320003;
    private static final int MOVE_LEFT = 320004;

    private static final int FRONT_SIDE = 570001;
    private static final int BACK_SIDE = 570002;

    private static final String CURRENT_CARD_NUMBER_KEY = "currentCardNumberKeyEasyEnglish";
    private static final String CARDS_TO_LEARN_KEY = "cardsToLearnKeyEasyEnglish";
    private static final String TOTAL_CARDS_NUMBER_KEY = "totalCardNumber";

    private int currentCardSide;
    private int currentCardNumber;

    private TextView wordView;
    private int wordViewHeight;
    private int wordViewWidth;
    private int moveY;
    private int moveX;
    private Point screenSize;

    private TextView wordCounter;
    private CircularProgressView wordCounterProgress;
    int totalCardNumber;

    private List<Card> cardsToLearn;
    private List<Long> cardIds;

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_word);

        toolBar = (Toolbar) findViewById(R.id.learn_word_act_app_bar);
        setSupportActionBar(toolBar);
        toolBar.getBackground().setAlpha(0);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wordCounter = (TextView) findViewById(R.id.learn_word_act_cards_left);
        wordCounterProgress = (CircularProgressView) findViewById(R.id.learn_word_act_progress_bar);

        cardsToLearn = new ArrayList<Card>();
        Intent intent = getIntent();
        cardIds = (List<Long>)intent.getSerializableExtra(VocabularyActivity.SELECTED_CARD_IDS);
        for (Long cardId: cardIds){
            cardsToLearn.add(Application.localStore.readWithRelations(cardId, Card.class));
        }
        Collections.shuffle(cardsToLearn);

        totalCardNumber = cardsToLearn.size();

        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        wordViewHeight = screenSize.y/2;
        wordViewWidth = screenSize.x/2;

        RelativeLayout.LayoutParams layoutParams = null;
        wordView = (TextView) findViewById(R.id.learn_word_act_tv_word);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, wordViewHeight);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        } else {
            layoutParams = new RelativeLayout.LayoutParams(wordViewWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        }
        layoutParams.topMargin = screenSize.y/4;
        layoutParams.leftMargin = screenSize.x/4;

        wordView.setLayoutParams(layoutParams);

        currentCardNumber = 0;
        showWordCard();

        wordView.setOnTouchListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this,PronunciationService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(this,PronunciationService.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable(CARDS_TO_LEARN_KEY, (ArrayList<Card>)cardsToLearn);
        savedInstanceState.putInt(CURRENT_CARD_NUMBER_KEY, currentCardNumber);
        savedInstanceState.putInt(TOTAL_CARDS_NUMBER_KEY, totalCardNumber);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        cardsToLearn = (List<Card>)savedInstanceState.getSerializable(CARDS_TO_LEARN_KEY);
        currentCardNumber = savedInstanceState.getInt(CURRENT_CARD_NUMBER_KEY);
        totalCardNumber = savedInstanceState.getInt(TOTAL_CARDS_NUMBER_KEY);
        showWordCard();
    }

    @Override
    public boolean onTouch(View wordView, MotionEvent event) {

        final int eventY = (int) event.getRawY(); //where finger is
        final int eventX = (int) event.getRawX();
        RelativeLayout.LayoutParams wordViewParams = (RelativeLayout.LayoutParams) wordView.getLayoutParams();

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                moveY = eventY - wordViewParams.topMargin;
                moveX = eventX - wordViewParams.leftMargin; //gg
                break;

            case MotionEvent.ACTION_MOVE:

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { //gg
                    wordViewParams.topMargin = eventY - moveY;
                } else {
                    wordViewParams.leftMargin = eventX - moveX; //gg
                }
                wordView.setLayoutParams(wordViewParams);
                break;

            case MotionEvent.ACTION_UP:

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    portraitAction(wordViewParams);
                } else {
                    landscapeAction(wordViewParams);
                }
                break;
        }
        return true;
    }

    private void landscapeAction(RelativeLayout.LayoutParams wordViewParams) {
        if (wordViewParams.leftMargin < screenSize.x/8) { //left part of the screen "I KNOW"

            if (currentCardNumber < cardsToLearn.size()) {
                moveWordViewAfterAction(wordView, MOVE_LEFT, (wordViewParams.leftMargin - wordViewWidth / 2));
            }

        } else if (wordViewParams.leftMargin > (screenSize.x*3/8)) {  //right part of the screen "I FORGOT"

            if (currentCardNumber < cardsToLearn.size()) {
                cardsToLearn.add(cardsToLearn.get(currentCardNumber));
                moveWordViewAfterAction(wordView, MOVE_RIGHT, (wordViewParams.leftMargin - wordViewWidth / 2));
            }

        } else if ((wordViewParams.leftMargin - screenSize.x/4) > -20 && (wordViewParams.leftMargin - screenSize.x/4) < 20) { //very small step "Translate"

            if (currentCardNumber < cardsToLearn.size()) {
                rotateWordCard();
            } else {
                onBackPressed();
                overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
            }

        }
        wordViewParams.leftMargin = screenSize.x/4;
        wordView.setLayoutParams(wordViewParams);
    }

    private void portraitAction(RelativeLayout.LayoutParams wordViewParams) {
        if (wordViewParams.topMargin < screenSize.y/8) { //upper part of the screen "I KNOW"

            if (currentCardNumber < cardsToLearn.size()) {
                moveWordViewAfterAction(wordView, MOVE_UP, (wordViewParams.topMargin - wordViewHeight / 2));
            }

        } else if (wordViewParams.topMargin > (screenSize.y*3/8)) {  //lower part of the screen "I FORGOT"

            if (currentCardNumber < cardsToLearn.size()) {
                cardsToLearn.add(cardsToLearn.get(currentCardNumber));
                moveWordViewAfterAction(wordView, MOVE_DOWN, (wordViewParams.topMargin - wordViewHeight / 2));
            }

        } else if ((wordViewParams.topMargin - screenSize.y/4) > -20 && (wordViewParams.topMargin - screenSize.y/4) < 20) { //very small step "Translate"

            if (currentCardNumber < cardsToLearn.size()) {
                rotateWordCard();
            } else {
                onBackPressed();
                overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
            }
        }
        wordViewParams.topMargin = screenSize.y/4;
        wordView.setLayoutParams(wordViewParams);
    }

    private void showWordCard() {
        if (wordView != null) {
            if (cardsToLearn.size() > currentCardNumber) {
                wordView.setText(cardsToLearn.get(currentCardNumber).getFront());
                currentCardSide = FRONT_SIDE;
            } else {
                wordView.setText(R.string.all_words_have_been_learned);
                wordView.setTextColor(ContextCompat.getColor(this, R.color.accent_light));
            }
        }
        setProgress();
    }

    private void setProgress () {
        int cardsLeft = cardsToLearn.size() - currentCardNumber;
        wordCounter.setText(String.valueOf(cardsLeft));
        float progressPercent = 100f - ((float)cardsLeft / (float)totalCardNumber)*100f;
        wordCounterProgress.setProgress(progressPercent);
        if (cardsLeft < 1) {
            wordCounterProgress.setVisibility(View.GONE);
            wordCounter.setVisibility(View.GONE);
        }
    }

    private void rotateWordCard() {
        if (wordView!= null) {
            if (currentCardSide == FRONT_SIDE) {
                wordView.setText(cardsToLearn.get(currentCardNumber).getBack());
                currentCardSide = BACK_SIDE;
            } else {
                wordView.setText(cardsToLearn.get(currentCardNumber).getFront());
                currentCardSide = FRONT_SIDE;
            }
        }
    }

    private void moveWordViewAfterAction (View wordView, int direction, int startPosition) {

        TranslateAnimation anim;
        switch (direction){
            case MOVE_UP:
                anim = new TranslateAnimation(0,0, startPosition, startPosition - screenSize.y/2);
                break;

            case MOVE_DOWN:
                anim = new TranslateAnimation(0,0, startPosition, startPosition + screenSize.y/2);
                break;

            case MOVE_LEFT:
                anim = new TranslateAnimation(startPosition, startPosition - screenSize.x/2, 0, 0);
                break;

            case MOVE_RIGHT:
                anim = new TranslateAnimation(startPosition, startPosition + screenSize.x/2,0,0);
                break;

            default:
                anim = null;
                break;
        }
        anim.setDuration(WORD_VIEW_MOVE_DURATION);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                currentCardNumber++;
                showWordCard();
            }
        });
        wordView.startAnimation(anim);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_learn_word, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
                return true;

            case R.id.learn_word_act_action_pronunciation:
                runPronounce ();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void runPronounce () {
        if (isNetworkConnected()) {
            if (currentCardNumber < cardsToLearn.size()) {
                String wordToPronounce = cardsToLearn.get(currentCardNumber).getBack();
                if (currentCardSide == BACK_SIDE && !wordToPronounce.isEmpty()) {
                    wordToPronounce = validateWordToPronounce(wordToPronounce);
                    Intent intent = new Intent(PronunciationService.PRONUNCIATION_TASK);
                    intent.putExtra(PronunciationService.WORD, wordToPronounce);
                    sendBroadcast(intent);
                }
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private String validateWordToPronounce(String word) {
        word = word.replace(" ", "_").toLowerCase();
        //remove "to" from verbs
        if (word.length() > 4 && word.substring(0,3).contentEquals("to_")) {
            word = word.substring(3);
        }
        Log.d(TAG, "Word to pronounce " + word);
        return word;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
