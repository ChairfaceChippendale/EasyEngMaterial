package com.ujujzk.ee.local.dictionary

import com.ujujzk.ee.data.gateway.dic.DicLocal
import com.ujujzk.ee.data.source.dic.local.model.toDomain
import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import com.ujujzk.ee.local.dictionary.model.ArticleRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DicStorageFake: DicLocal {

    override fun observeDictionaries(): Flow<List<Dictionary>> {
        return flowOf(
            listOf(
                Dictionary("d1", "Oxford Dictionary"),
                Dictionary("d2", "Universal Dictionary"),
                Dictionary("d3", "Urban Dictionary"),
                Dictionary("d4", "DSL Vok"),
                Dictionary("d5", "Ru-En"),
                Dictionary("d6", "Very Long name Dictionary Very long"),
                Dictionary("d7", "Oxford Dictionary"),
                Dictionary("d8", "Oxford Dictionary"),
                Dictionary("d9", "Oxford Dictionary"),
                Dictionary("d10", "Oxford Dictionary"),
                Dictionary("d11", "Oxford Dictionary"),
                Dictionary("d12", "Oxford Dictionary"),
                Dictionary("d13", "Oxford Dictionary"),
                Dictionary("d14", "Oxford Dictionary"),
                Dictionary("d15", "Oxford Dictionary"),
                Dictionary("d16", "Oxford Dictionary"),
                Dictionary("d17", "Oxford Dictionary"),
                Dictionary("d18", "Oxford Dictionary"),
                Dictionary("d19", "Oxford Dictionary"),
                Dictionary("d120", "Oxford Dictionary"),
                Dictionary("d163", "Oxford Dictionary")
            )
        )
    }


    override suspend fun testArticleParser(): Article {
        return ArticleRoom(
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
        ).toDomain()
    }
}