package com.ujujzk.easyengmaterial.eeapp.model;

import com.github.aleksandrsavosh.simplestore.Base;

public class Word extends Base implements Comparable {

    private String wordName;

    public Word () {}

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
        return "Word :" + wordName + " " + super.toString();
    }

    @Override
    public int compareTo(Object anotherWord) {
        if (anotherWord instanceof Word){
            return wordName.compareToIgnoreCase(((Word) anotherWord).getWordName());
        }
        return -1;
    }


}
