package com.ujujzk.easyengmaterial.eeapp.util;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.VocabularyActivity;
import com.ujujzk.easyengmaterial.eeapp.model.Card;

import java.util.ArrayList;
import java.util.List;

public class LearnWordActivity extends AppCompatActivity implements View.OnTouchListener{

    @SuppressWarnings("unused")
    private static final String TAG = LearnWordActivity.class.getSimpleName();

    private static final int MOVE_UP = 320001;
    private static final int MOVE_DOWN = 320002;
    private static final int MOVE_RIGHT = 320003;
    private static final int MOVE_LEFT = 320004;

    private static final int FRONT_SIDE = 570001;
    private static final int BACK_SIDE = 570002;
    private int currentSide;

    private TextView wordView;
    private int wordViewHeight;
    private int wordViewWidth;
    private int moveY;
    private int moveX;
    private Point screenSize;

    List<Card> cardsToLearn;
    List<String> cardIds;

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

        cardsToLearn = new ArrayList<Card>();
        cardIds = new ArrayList<String>();
        Intent intent = getIntent();
        cardIds = intent.getStringArrayListExtra(VocabularyActivity.SELECTED_CARD_IDS);
        for (String cardId: cardIds){
            cardsToLearn.add(Application.cardLocalCrudDao.readWithRelations(cardId));
        }

        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        wordViewHeight = screenSize.y/2;
        wordView = (TextView) findViewById(R.id.learn_word_act_tv_word);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, wordViewHeight);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.topMargin = screenSize.y/4;
        wordView.setLayoutParams(layoutParams);

        wordView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View wordView, MotionEvent event) {

        final int eventY = (int) event.getRawY(); //where finger is
        RelativeLayout.LayoutParams wordViewParams = (RelativeLayout.LayoutParams) wordView.getLayoutParams();

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                moveY = eventY - wordViewParams.topMargin;  //difference between the Touch-event and the topMargin of the text
                break;

            case MotionEvent.ACTION_MOVE:

                wordViewParams.topMargin = eventY - moveY;
                wordView.setLayoutParams(wordViewParams);
                break;

            case MotionEvent.ACTION_UP:

                if (wordViewParams.topMargin < screenSize.y/8) { //upper part of the screen "I KNOW"

                    moveWordViewAfterAction(wordView, MOVE_UP, (wordViewParams.topMargin - wordViewHeight/2));
                    //TODO next word

                } else if (wordViewParams.topMargin > (screenSize.y*3/8)) {  //lower part of the screen "I FORGOT"

                    moveWordViewAfterAction(wordView, MOVE_DOWN, (wordViewParams.topMargin - wordViewHeight/2));
                    //TODO next word, current word to the pack end

                } else if ((wordViewParams.topMargin - screenSize.y/4) > -20 && (wordViewParams.topMargin - screenSize.y/4) < 20) { //very small step "Translate"

                    //TODO translate word
                    Toast.makeText(this, "Translate", Toast.LENGTH_SHORT).show();

                } else {

                }
                wordViewParams.topMargin = screenSize.y/4;
                wordView.setLayoutParams(wordViewParams);
                break;
        }

        return true;
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

            default:
                anim = null;
                break;
        }
        anim.setDuration(400);
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
                return true;

            case R.id.learn_word_act_action_pronunciation:
                //TODO
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }



}
