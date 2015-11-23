package com.ujujzk.easyengmaterial.eeapp.model;


import com.github.aleksandrsavosh.simplestore.Base;

public class Rule extends Base {

    private String rule;


    public Rule(){

    }

    public Rule(String rule) {
        this.rule = rule;
    }

    public String getRule() {
        return rule;
    }
}
