package com.ujujzk.ee.data.gateway.dic

import com.ujujzk.ee.domain.gateway.DictionaryGateway

class DicGatewayImpl (
    private val dicLocal: DicLocal
) : DictionaryGateway {

    override fun observeDictionaries() =
        dicLocal.observeDictionaries()


    //TODO TEMP FOR TEST
    override suspend fun testArticleParser() =
        dicLocal.testArticleParser()

}