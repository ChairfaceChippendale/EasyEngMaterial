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
                squareBracketText = "[com]\\\\(ˈ)a|ränik, -e|-, -aa|-, -ā|-\\\\[/com] [i][c][com]adjective[/com][/c][/i]\n" +
                        " [m1][com][*][b]Usage:[/b] usually capitalized[/*][/com][/m]\n" +
                        " [m1][com][*][b]Etymology:[/b] [i]Aaron fl ab [/i]1200 B.C. Jewish patriarch & high priest, brother of Moses + English [i]-ic[/i][/*][/com][/m]\n" +
                        " [m1][b][c darkslategray]1.[/c][/b] [trn] [b]:[/b] of or stemming from Aaron the Levite, the first high priest of the Hebrews[/trn][/m]\n" +
                        " [m1][b][c darkslategray]2.[/c][/b] [trn] [b]:[/b] of, belonging to, or being the lesser order of priesthood in the Mormon church comprising the grades of deacon, teacher, and priest[/trn] — compare [ref]melchizedek[/ref][/m]",
                wordName = "wardname",
                dictionaryId = "1",
                dictionaryName = "dic1"
            )
        ))
    }

}