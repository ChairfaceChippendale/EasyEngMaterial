package com.ujujzk.easyengmaterial.eeapp.model;


import com.github.aleksandrsavosh.simplestore.Base;

import java.util.ArrayList;
import java.util.List;

public class Topic extends Base {

    private String title;
    private List<Task> tasks;
    private String ruleId;

    public Topic() {
    }



    public Topic(String title, List<Task> tasks, String ruleId) {
        this.title = title;

        this.tasks = new ArrayList<Task>(tasks);
        this.ruleId = ruleId;
    }

    public void addTask (Task task) {
        tasks.add(task);
    }

    public Task getTask (int num) {
        return tasks.get(num);
    }

    public int getTasksNumber () {
        return tasks.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public String getRuleId (){
        return ruleId;
    }



}
