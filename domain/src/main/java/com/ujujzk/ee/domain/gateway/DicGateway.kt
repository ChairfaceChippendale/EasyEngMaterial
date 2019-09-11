package com.ujujzk.ee.domain.gateway

import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable

interface DicGateway {

    fun observeDictionaries(): Flowable<List<Dictionary>>

}