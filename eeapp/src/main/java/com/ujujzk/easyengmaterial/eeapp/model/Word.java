package com.ujujzk.easyengmaterial.eeapp.model;


import com.github.aleksandrsavosh.simplestore.Base;

public class Word extends Base{
    String wordName;

    public Word(String wordName) {
        this.wordName = wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getWordName() {
        return wordName;
    }

    @Override
    public String toString() {
        return "Word :" + wordName;
    }
}
