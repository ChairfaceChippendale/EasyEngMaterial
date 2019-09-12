package com.ujujzk.ee.domain.gateway

import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable
import io.reactivex.Single

interface DicGateway {

    fun observeDictionaries(): Flowable<List<Dictionary>>

    //TODO TEMP
    fun ddd(): Single<Article>

}