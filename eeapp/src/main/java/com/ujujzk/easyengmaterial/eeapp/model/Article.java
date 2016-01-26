package com.ujujzk.easyengmaterial.eeapp.model;


import com.github.aleksandrsavosh.simplestore.Base;

public class Article extends Base {
    String article;
    Long wordId;
    Long dictionaryId;

    public Article(){}

    public Article(String article, Long wordId, Long dictionaryId) {
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
