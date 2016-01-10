package com.ujujzk.easyengmaterial.eeapp.model;


import com.github.aleksandrsavosh.simplestore.Base;

public class WordArticle extends Base {
    String article;
    long wordId;
    long dictionaryId;

    public WordArticle(){}

    public WordArticle(String article, long wordId, long dictionaryId) {
        this.article = article;
        this.wordId = wordId;
        this.dictionaryId = dictionaryId;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public void setDictionaryId(long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getArticle() {
        return article;
    }

    public long getWordId() {
        return wordId;
    }

    public long getDictionaryId() {
        return dictionaryId;
    }

    @Override
    public String toString() {
        return "WordArticle{" +
                "article='" + article + '\'' +
                ", wordId=" + wordId +
                ", dictionaryId=" + dictionaryId +
                "} " + super.toString();
    }
}
