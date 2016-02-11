package com.ujujzk.easyengmaterial.eeapp.model;

import com.github.aleksandrsavosh.simplestore.Base;

public class Word extends Base implements Comparable {

    private String wordName;
    private String dictionaryName;


    public Word () {}

    public Word(String wordName, String dictionaryName) {
        this.wordName = wordName;
        this.dictionaryName = dictionaryName;
    }

    public String getWordName() {
        return wordName;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordName=" + wordName +
                ", dictionaryName=" + dictionaryName +
                "} " + super.toString();
    }

    @Override
    public int compareTo(Object anotherWord) {
        if (anotherWord instanceof Word){
            return wordName.compareToIgnoreCase(((Word) anotherWord).getWordName());
        }
        return -1;
    }


}
