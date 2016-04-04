package com.ujujzk.easyengmaterial.eeapp.model;


import android.util.Log;
import com.github.aleksandrsavosh.simplestore.Base;

public class Article extends Base {

    private String squareBracketText;
    private String wordName;
    private Long dictionaryId;

    public Article(){}

    public Article(String squareBracketText, String wordName, Long dictionaryId) {
        this.squareBracketText = squareBracketText;
        this.dictionaryId = dictionaryId;
        this.wordName = wordName;
    }

    public String getWordName() {
        return wordName;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    @Override
    public String toString() {
        return "WordArticle{" +
                "article=" + squareBracketText +
                ", dictionaryName=" + dictionaryId +
                "} " + super.toString();
    }

    public String getArticleHTMLStyle(){

        String article = squareBracketText;

        Log.d("Model Article", article);

        article = article.replaceAll("\\[/?(\\*|!trs|'|trn|com)\\]", "")
                .replace("{", "")
                .replace("}", "")
                .replaceAll("\\[(s|url)\\][^\\[]*\\[/(s|url)\\]", "")

                .replaceAll("\\[/?lang[^\\[]*\\]", "").replaceAll("\\[c\\](\\[com\\])?", "<font color=\'#2e7d32\'>") //green
                .replace("[c]", "<font color=\'#2e7d32\'>")           //green
                .replaceAll("(\\[/com\\])?\\[/c\\]", "</font>")

                .replace("[p]", "<font color=\'#2e7d32\'><i>")        //green
                .replace("[/p]", "</i></font>")

                .replaceAll("\\[(ex|c gray)\\]", "<font color=\'#757575\'>") //grey
                .replaceAll("\\[/(ex|com)\\]", "</font>")


//                .replaceAll("\\[ref[^\\]]*\\]", "<font color=\'#283593\'>") //blue
//                .replaceAll("\\[/ref\\]", "</font>")
                .replaceAll("\\[ref[^\\]]*\\]([^\\]]*)\\[/ref\\]","<a href='$1'>$1</a>")

                .replaceAll("\\[m[1-9]?\\]", "")
                .replace("[/m]", "")

                .replace("[b]", "<b>")
                .replace("[i]", "<i>")
                .replace("[u]", "<u>")
                .replace("[/b]", "</b>")
                .replace("[/i]", "</i>")
                .replace("[/u]", "</u>")

                .replace("\\[", "[")
                .replace("\\]", "]")
                .replaceAll("\\[\\[t\\][^\\[]*\\[/t\\]\\],?", "");

        article = article.substring(article.indexOf("\t")+1)
                .replace("\t", "")
                .trim()
                .replace("\n", "<br>");

        return article;
    }
}
