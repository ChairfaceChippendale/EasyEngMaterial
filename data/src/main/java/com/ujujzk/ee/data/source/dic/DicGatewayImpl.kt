package com.ujujzk.ee.data.source.dic

import com.ujujzk.ee.data.source.dic.local.model.ArticleRoom
import com.ujujzk.ee.data.tools.mapper.MapperDelegate
import com.ujujzk.ee.domain.gateway.DicGateway
import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable
import io.reactivex.Single

class DicGatewayImpl (
    private val dicStorage: DicStorage,
    private val mapperDelegate: MapperDelegate
) : DicGateway {

    override fun observeDictionaries(): Flowable<List<Dictionary>> =
        dicStorage.observeDictionaries()


    //TODO TEMP
    override fun ddd(): Single<Article> {

        return Single.just(mapperDelegate.convert<ArticleRoom, Article>(
            ArticleRoom(
                id = "1",
                squareBracketText = "   [s]Qualität.wav[/s]\n" +
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
                        "   [m1]7) [p][trn]фон.[/p] окраска, качество тембра [com]([i]гласного[/i])[/com][/trn][/m]",
                wordName = "wardname",
                dictionaryId = "1",
                dictionaryName = "dic1"
            )
        ))
    }

}