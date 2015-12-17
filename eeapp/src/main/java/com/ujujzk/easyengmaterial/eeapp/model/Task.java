package com.ujujzk.easyengmaterial.eeapp.model;


import com.github.aleksandrsavosh.simplestore.Base;

import java.util.ArrayList;
import java.util.List;


public class Task extends Base {

    private String question;
    private List<Answer> answers;
    private String rightAnswerId;
    private String hint;

    public Task() {
        question = "";
        answers = new ArrayList<Answer>();
        rightAnswerId = "";
        hint = "";
    }

    public Task(String question, List<Answer> answers, String rightAnswerId, String hint) {
        this.question = question;
        this.answers = new ArrayList<Answer>(answers);
        this.rightAnswerId = rightAnswerId;
        this.hint = hint;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getRightAnswerId() {
        return rightAnswerId;
    }

    public String getHint() {
        return hint;
    }
}
