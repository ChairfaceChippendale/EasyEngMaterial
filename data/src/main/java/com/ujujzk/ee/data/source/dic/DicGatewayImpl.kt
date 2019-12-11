package com.ujujzk.ee.data.source.dic

import android.util.Log
import com.ujujzk.ee.data.source.dic.local.model.ArticleRoom
import com.ujujzk.ee.data.source.dic.local.model.Key
import com.ujujzk.ee.data.tools.mapper.MapperDelegate
import com.ujujzk.ee.domain.gateway.DicGateway
import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable
import io.reactivex.Single

class DicGatewayImpl (
    private val dicStorage: DicStorage,
    private val mapperDelegate: MapperDelegate,
    private val logger: (String) -> Unit
) : DicGateway {

    override fun observeDictionaries(): Flowable<List<Dictionary>> =
        dicStorage.observeDictionaries()


    //TODO TEMP
    override fun ddd(): Single<Article> {

        val s = "   [s]Qualität.wav[/s]\n" +
                "   [m1][p][i][c][com]f[/p] [p]=[/i][/p], [lang id=32775]-en[/lang][/com][/c][/m]\n" +
                "   [m1]1) [trn]качество, свойство[/trn][/m]\n" +
                "   [m2][*][ex][lang id=32775]Qualität I. \\[erster\\] Wahl[/lang] — качество первого сорта[/ex][/*][/m]\n" +
                "   [m1]2) [trn]качество, достоинство[/trn][/m]\n" +
                "   [m2][*][ex][lang id=32775]er hat in der Stunde der Gefahr seine Qualitäten bewiesen[/lang] — в момент опасности он проявил свои достоинства \\[свои лучшие качества\\][/ex][/*][/m]\n" +
                "   [m2][*][ex][lang id=32775]ein Mann von Qualitäten[/lang] — человек с большими достоинствами[/ex][/*][/m]\n" +
                "   [m1]3) [trn][com]([p][i]сокр.[/p] [lang id=32775]Q[/i])[/lang][/com] (высшее) качество; [p]ком.[/p] сорт[/trn][/m]\n" +
                "   [m2][*][ex][lang id=32775]beste Qualität[/lang] — высший сорт[/ex][/*][/m]\n" +
                "   [m2][*][ex][lang id=32775]diese Ware ist Qualität[/lang] — это товар высшего качества[/ex][/*][/m]\n" +
                "   [m2][p][*][ex][lang id=32775]etw.[/p] auf die Qualität überprüfen[/lang] — проверить \\[определить\\] качество \\[добротность\\] [p]чего-л.[/ex][/*][/p][/m]\n" +
                "   [m2][*][ex][lang id=32775]von guter Qualität[/lang] — доброкачественный [com]([i]о товаре[/i])[/com][/ex][/*][/m]\n" +
                "   [m2][*][ex][lang id=32775]von schlechter Qualität[/lang] — недоброкачественный [com]([i]о товаре[/i])[/com][/ex][/*][/m]\n" +
                "   [m2][*][ex][lang id=32775]um hohe Qualität wetteifern[/lang] — бороться за высокое качество[/ex][/*][/m]\n" +
                "   [m1]4) [trn]титул, звание, ранг[/trn][/m]\n" +
                "   [m1]5) [trn]качество, уровень[/trn][/m]\n" +
                "   [m2][*][ex][lang id=32775]erfinderische Qualität[/lang] — уровень изобретения[/ex][/*][/m]\n" +
                "   [m1]6) [trn]класс [com]([i]машин[/i])[/com][/trn][/m]\n" +
                "   [m1]7) [p][trn]фон.[/p] окраска, качество тембра [com]([i]гласного[/i])[/com][/trn][/m]"

        val s2 = " [b][c darkslategray]I. [/c][/b][i][c][com]transitive verb[/com][/c][/i]\n" +
                " [i]also[/i] [b]braize[/b] [com]\\\\ˈbrāz\\\\[/com]\n" +
                " [com]([b]-ed/-ing/-s[/b])[/com]\n" +
                " [m1][com][*][b]Etymology:[/b] French [i]braiser, [/i]from [i]braise [/i]live coals, from Old French [i]brese[/i] — more at [ref]braze[/ref][/*][/com][/m]\n" +
                " [m1][trn] [b]:[/b] to cook (meat or vegetables) slowly in fat and little moisture in a tightly closed pot[/trn][/m]\n" +
                " [b][c darkslategray]II. [/c][/b][i][c][com]noun[/com][/c][/i]\n" +
                " [i]also[/i] [b]braize[/b] [com]\\\\“\\\\[/com]\n" +
                " [com]([b]-s[/b])[/com]\n" +
                " [m1][trn] [b]:[/b] an item of braised food[/trn][/m]\n" +
                " [m2][*][ex]squab [i]braise[/i][/ex][/*][/m]\n" +
                " [b][c darkslategray]III. [/c][/b][i][c][com]noun[/com][/c][/i]\n" +
                " [i]or[/i] [b]braize[/b] [com]\\\\“\\\\[/com]\n" +
                " [com]([b]-s[/b])[/com]\n" +
                " [m1][com][*][b]Etymology:[/b] probably from Middle Low German [i]brassen, bressem [/i]bream — more at [ref]bream[/ref][/*][/com][/m]\n" +
                " [m1][trn] [b]:[/b] a European sea bream ([i]Pagrus pagrus[/i])[/trn][/m]"

        val k = Key()
        Log.w("C++", "start")
        var sd: String = ""
        for (i in 0..10) {
            sd = k.getKey(s)
        }
        Log.w("C++", sd)



        Log.w("JAVA", "start")
        var article = s
        for (i in 0..10){


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
        }


        Log.w("JAVA", article)






        return Single.just(mapperDelegate.convert<ArticleRoom, Article>(
            ArticleRoom(
                id = "1",
                squareBracketText = s,
                wordName = "wardname",
                dictionaryId = "1",
                dictionaryName = "dic1"
            )
        ))
    }

}