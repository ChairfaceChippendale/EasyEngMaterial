package com.ujujzk.easyengmaterial.eeapp.grammar;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Answer;
import com.ujujzk.easyengmaterial.eeapp.model.Task;
import com.ujujzk.easyengmaterial.eeapp.model.Topic;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExerciseActivity extends AppCompatActivity {

    private static final String NEXT_TASK_NUMBER = "nextTaskNumberKeyEasyEnglish";
    private static final String TASKS_TO_LEARN = "tasksToLearnKeyEasyEnglish";

    private Toolbar toolBar;
    private List<Task> tasksToLearn;
    private View taskBox;
    private CircularProgressView progressBar;
    private TextView exerciseQuestion;
    private ListView exerciseAnswers;
    ArrayAdapter<Answer> answerListAdapter;
    private int nextTaskNumber;
    private TextView hint;
    private FABToolbarLayout hintLayout;
    private Button nextTaskBtn;

    @SuppressWarnings("unused")
    private static final String TAG = ExerciseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolBar = (Toolbar) findViewById(R.id.exercise_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exerciseQuestion = (TextView) findViewById(R.id.exercise_act_tv_question);
        exerciseAnswers = (ListView) findViewById(R.id.exercise_act_lv_answer_list);
        hint = (TextView) findViewById(R.id.exercise_act_tv_hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintLayout.hide();
            }
        });
        hintLayout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        nextTaskBtn = (Button) findViewById(R.id.exercise_act_next_btn);
        nextTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTask();
            }
        });

        answerListAdapter = new ArrayAdapter<Answer>(this, R.layout.answer_list_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Answer answer = getItem(position);
                ((TextView) view).setText(answer.getAnswer());
                ((TextView) view).setTextColor(Color.BLACK);
                return view;
            }
        };
        exerciseAnswers.setAdapter(answerListAdapter);
        exerciseAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAnswerId = tasksToLearn.get(nextTaskNumber-1).getAnswers().get(position).getCloudId();
                String rightAnswerId    = tasksToLearn.get(nextTaskNumber-1).getRightAnswerId();
                if (selectedAnswerId.equals(rightAnswerId)){
                    //view.setBackgroundColor(Color.GREEN);
                    ((TextView)view).setTextColor(ContextCompat.getColor(ExerciseActivity.this, R.color.eeapp_green));
                } else {
                    //view.setBackgroundColor(Color.RED);
                    ((TextView)view).setTextColor(ContextCompat.getColor(ExerciseActivity.this, R.color.eeapp_red));
                }
                nextTaskBtn.setVisibility(View.VISIBLE);
            }
        });

        taskBox = findViewById(R.id.exercise_act_task_box);
        progressBar = (CircularProgressView) findViewById(R.id.exercise_act_progress_bar);

        getData(getIntent(), savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);

        instanceState.putInt(NEXT_TASK_NUMBER, nextTaskNumber);
        instanceState.putSerializable(TASKS_TO_LEARN, (ArrayList<Task>)tasksToLearn);

    }

    private void getData(final Intent intent, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            tasksToLearn = (List<Task>) savedInstanceState.getSerializable(TASKS_TO_LEARN);
            nextTaskNumber = savedInstanceState.getInt(NEXT_TASK_NUMBER);
            if (nextTaskNumber > 0){
                nextTaskNumber--;
            }
            showTask();
        } else {
            new AsyncTask<Void, Void, List<Task>>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                    taskBox.setVisibility(View.GONE);
                    hintLayout.setVisibility(View.GONE);
                }
                @Override
                protected List<Task> doInBackground(Void... params) {
                    List<String> topicIds = intent.getStringArrayListExtra(GrammarActivity.SELECTED_TOPICS_IDS);
                    List<Topic> topics = new ArrayList<Topic>();
                    for (String topicId : topicIds) {
                        topics.add(Application.cloudStore.readWithRelations(topicId, Topic.class));
                    }
                    List<Task> tasks = new ArrayList<Task>();

                    for (Topic topic : topics) {
                        List<Task> t = topic.getAllTasks();
                        for (Task task : t) {
                            tasks.add(task);
                        }
                    }
                    Collections.shuffle(tasks);
                    return tasks;
                }
                @Override
                protected void onPostExecute(List<Task> tasks) {
                    super.onPostExecute(tasks);
                    tasksToLearn = tasks;
                    nextTaskNumber = 0;
                    showTask();
                    progressBar.setVisibility(View.GONE);
                    taskBox.setVisibility(View.VISIBLE);
                    hintLayout.setVisibility(View.VISIBLE);
                }
            }.execute();
        }
    }

    private void showTask() {
        if (tasksToLearn.size() > nextTaskNumber) {
            Task task = tasksToLearn.get(nextTaskNumber);
            exerciseQuestion.setText(task.getQuestion());
            hint.setText(task.getHint().replaceAll("\\\\n", "\n"));
            answerListAdapter.clear();
            List<Answer> answers = task.getAnswers();
            Collections.shuffle(answers);
            answerListAdapter.addAll(answers);
            answerListAdapter.notifyDataSetChanged();
            nextTaskNumber++;
            hintLayout.hide();
            if (tasksToLearn.size() == nextTaskNumber){
                nextTaskBtn.setText(getResources().getString(R.string.finish_exercise));
            }
            nextTaskBtn.setVisibility(View.GONE);

            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(getResources().getString(R.string.exercise_title_start) + " " + nextTaskNumber + "/" + tasksToLearn.size());
            }
        } else {
            onBackPressed();
            overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
        }
    }

}
