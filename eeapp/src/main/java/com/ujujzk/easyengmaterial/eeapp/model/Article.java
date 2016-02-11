package com.ujujzk.easyengmaterial.eeapp.model;


import com.github.aleksandrsavosh.simplestore.Base;

public class Article extends Base {

    private String squareBracketText;
    private String wordName;
    private String dictionaryName;

    public Article(){}

    public Article(String squareBracketText, String wordName, String dictionaryName) {
        this.squareBracketText = squareBracketText;
        this.dictionaryName = dictionaryName;
        this.wordName = wordName;
    }


    public String getWordName() {
        return wordName;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    @Override
    public String toString() {
        return "WordArticle{" +
                "article=" + squareBracketText +
                ", dictionaryName=" + dictionaryName +
                "} " + super.toString();
    }

    public String getArticleHTMLStyle(){

        String article = squareBracketText;

        article = article.replaceAll("\\[/?(\\*|!trs|'|trn|com)\\]", "");
        article = article.replace("{", "");
        article = article.replace("}", "");
        article = article.replaceAll("\\[(s|url)\\][^\\[]*\\[/(s|url)\\]", "");
        article = article.replaceAll("\\[/?lang[^\\[]*\\]", "");

        article = article.replaceAll("\\[c\\](\\[com\\])?", "<font color=\'#2e7d32\'>");
        article = article.replaceAll("(\\[/com\\])?\\[/c\\]", "</font>");
        article = article.replace("[c]", "<font color=\'#2e7d32\'>");
        article = article.replace("[p]", "<font color=\'#2e7d32\'><i>");
        article = article.replace("[/p]", "</i></font>");
        article = article.replaceAll("\\[(ex|c gray)\\]", "<font color=\'#757575\'>");
        article = article.replaceAll("\\[/(ex|com)\\]", "</font>");
        article = article.replace("[ref]", "<font color=\'#283593\'>");
        article = article.replace("[/ref]", "</font>");
        article = article.replaceAll("\\[m[1-9]?\\]", "");
        article = article.replace("[/m]", "<br>");

        article = article.replace("[b]", "<b>");
        article = article.replace("[i]", "<i>");
        article = article.replace("[u]", "<u>");
        article = article.replace("[/b]", "</b>");
        article = article.replace("[/i]", "</i>");
        article = article.replace("[/u]", "</u>");

        article = article.replace("\\[", "[");
        article = article.replace("\\]", "]");

        article = article.replaceAll("\\[\\[t\\][^\\[]*\\[/t\\]\\]", "");

        article = article.substring(article.indexOf("\t")+1);

        article = article.replace("\t", "");

        return article;
    }
}
