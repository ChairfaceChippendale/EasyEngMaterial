package com.ujujzk.ee.data.source.dic

import com.ujujzk.ee.domain.gateway.DicGateway
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable

class DicGatewayImpl (
    private val dicStorage: DicStorage
) : DicGateway {

    override fun observeDictionaries(): Flowable<List<Dictionary>> =
        dicStorage.observeDictionaries()

}