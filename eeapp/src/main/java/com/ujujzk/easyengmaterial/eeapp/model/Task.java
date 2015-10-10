package com.ujujzk.easyengmaterial.eeapp.model;

public class Task extends Base {

    private String taskType;
    private String question;
    private String answer;
    private String ruleId;

    public Task() {
    }

    public Task(String taskType, String question, String answer, String ruleId) {

        this.taskType = taskType;
        this.question = question;
        this.answer = answer;
        this.ruleId = ruleId;
    }



    public String getTaskType() { return taskType; }

    public void setTaskType(String taskType) { this.taskType = taskType; }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRuleId() {
        return ruleId;
    }



}
