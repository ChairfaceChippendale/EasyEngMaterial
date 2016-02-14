package com.ujujzk.easyengmaterial.eeapp.model;

import com.github.aleksandrsavosh.simplestore.Base;

public class Word extends Base implements Comparable {

    private String wordName;
    private Long dictionaryId;


    public Word () {}

    public Word(String wordName, Long dictionaryId) {
        this.wordName = wordName;
        this.dictionaryId = dictionaryId;
    }

    public String getWordName() {
        return wordName;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordName=" + wordName +
                ", dictionaryName=" + dictionaryId +
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
