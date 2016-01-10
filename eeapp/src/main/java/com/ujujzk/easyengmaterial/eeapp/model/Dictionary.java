package com.ujujzk.easyengmaterial.eeapp.model;

import com.github.aleksandrsavosh.simplestore.Base;

public class Dictionary extends Base {

    private String dictionaryName;

    public Dictionary() {}

    public Dictionary(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    @Override
    public String toString() {
        return "Dictionary :" + dictionaryName + " " + super.toString();
    }
}
