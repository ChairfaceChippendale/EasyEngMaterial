package com.ujujzk.ee.data.source.dic.local.model

import com.ujujzk.ee.data.tools.mapper.DataMapper
import com.ujujzk.ee.data.tools.mapper.MapperDelegate
import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary

object DictionaryFromRoomToDomain : DataMapper<DictionaryRoom, Dictionary> {
    override fun convert(from: DictionaryRoom, mapper: MapperDelegate): Dictionary {
        return from.run {
            Dictionary(
                id = id,
                dictionaryName = name
            )
        }
    }
}

object DictionariesFromRoomToDomain : DataMapper<List<DictionaryRoom>, List<Dictionary>> {
    override fun convert(from: List<DictionaryRoom>, mapper: MapperDelegate): List<Dictionary> {
        return from.map { mapper.convert<DictionaryRoom, Dictionary>(it) }
    }
}


object ArticleFromRoomToDomain : DataMapper<ArticleRoom, Article> {
    override fun convert(from: ArticleRoom, mapper: MapperDelegate): Article {
        return from.run {
            Article(
                id = id,
                wordName = wordName,
                wordDefinition = convertWordDefinition(squareBracketText),
                dictionaryId = dictionaryId,
                dictionaryName = dictionaryName
            )
        }
    }

    private fun convertWordDefinition(squareBracketText: String): String {

        var article = squareBracketText

        article = article
            .replace("\\\\\\\\(?:(?!\\\\\\\\).)+\\\\\\\\".toRegex(), "")

            .replace("\\[/?(\\*|!trs|'|trn|com)\\]".toRegex(), "")
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
            .replace("\\[(c darkslategray)\\]".toRegex(), "<font color=\'#2f4f4f\'>")
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

object ArticlesFromRoomToDomain : DataMapper<List<ArticleRoom>, List<Article>> {
    override fun convert(from: List<ArticleRoom>, mapper: MapperDelegate): List<Article> {
        return from.map { mapper.convert<ArticleRoom, Article>(it) }
    }
}

