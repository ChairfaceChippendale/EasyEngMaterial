package com.ujujzk.easyengmaterial.eeapp.model;


import java.util.ArrayList;
import java.util.List;


public class Task extends Base {

    private String question;
    private List<Answer> answers;
    private Answer rightAnswer;
    private String hint;

    public Task() {
        question = "";
        answers = new ArrayList<Answer>();
        rightAnswer = new Answer();
        hint = "";
    }

    public Task(String question, List<Answer> answers, Answer rightAnswer, String hint) {
        this.question = question;
        this.answers = new ArrayList<Answer>(answers);
        this.rightAnswer = rightAnswer;
        this.hint = hint;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Answer getRightAnswer() {
        return rightAnswer;
    }

    public String getHint() {
        return hint;
    }
}
