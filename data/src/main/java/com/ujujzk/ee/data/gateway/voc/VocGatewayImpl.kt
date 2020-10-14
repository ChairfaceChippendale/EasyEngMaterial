package com.ujujzk.ee.data.gateway.voc

import com.ujujzk.ee.domain.gateway.VocabularyGateway
import kotlinx.coroutines.delay

class VocGatewayImpl (

): VocabularyGateway {

    override suspend fun testVoc() {
        delay(1000)
    }



}