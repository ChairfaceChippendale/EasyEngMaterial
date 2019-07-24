package com.ujujzk.ee.domain.usecase.dic.model

data class Article (
    val id: String,
    val wordName: String,
    val squareBracketText: String,
    val dictionaryId: String
) {
    //green
    //green
    //green
    //grey
    //                .replaceAll("\\[ref[^\\]]*\\]", "<font color=\'#283593\'>") //blue
    //                .replaceAll("\\[/ref\\]", "</font>")
    val articleHTMLStyle: String
        get() {

            var article = squareBracketText


            article = article.replace("\\[/?(\\*|!trs|'|trn|com)\\]".toRegex(), "")
                .replace("{", "")
                .replace("}", "")
                .replace("\\[(s|url)\\][^\\[]*\\[/(s|url)\\]".toRegex(), "")

                .replace("\\[/?lang[^\\[]*\\]".toRegex(), "")
                .replace("\\[c\\](\\[com\\])?".toRegex(), "<font color=\'#2e7d32\'>")
                .replace("[c]", "<font color=\'#2e7d32\'>")
                .replace("(\\[/com\\])?\\[/c\\]".toRegex(), "</font>")

                .replace("[p]", "<font color=\'#2e7d32\'><i>")
                .replace("[/p]", "</i></font>")

                .replace("\\[(ex|c gray)\\]".toRegex(), "<font color=\'#757575\'>")
                .replace("\\[/(ex|com)\\]".toRegex(), "</font>")
                .replace("\\[ref[^\\]]*\\]([^\\]]*)\\[/ref\\]".toRegex(), "<a href='$1'>$1</a>")

                .replace("\\[m[1-9]?\\]".toRegex(), "")
                .replace("[/m]", "")

                .replace("[b]", "<b>")
                .replace("[i]", "<i>")
                .replace("[u]", "<u>")
                .replace("[/b]", "</b>")
                .replace("[/i]", "</i>")
                .replace("[/u]", "</u>")

                .replace("[sup]", "<sup>")
                .replace("[sub]", "<sub>")
                .replace("[/sup]", "</sup>")
                .replace("[/sub]", "</sub>")

                .replace("\\[", "[")
                .replace("\\]", "]")
                .replace("\\[\\[t\\][^\\[]*\\[/t\\]\\],?".toRegex(), "")

            article = article.substring(article.indexOf("\t") + 1)
                .replace("\t", "")
                .trim { it <= ' ' }
                .replace("\n", "<br>")

            return article
        }

}
