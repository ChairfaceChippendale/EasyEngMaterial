package com.ujujzk.easyengmaterial.eeapp.model;


import android.media.audiofx.Equalizer;
import com.github.aleksandrsavosh.simplestore.Base;

public class Word extends Base implements Comparable {
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

    @Override
    public int compareTo(Object anotherWord) {
        if (anotherWord instanceof Word){
            return wordName.compareTo(((Word) anotherWord).getWordName());
        }
        return -1;
    }


}
