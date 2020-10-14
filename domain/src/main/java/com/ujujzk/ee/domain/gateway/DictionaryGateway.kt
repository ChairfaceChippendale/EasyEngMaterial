package com.ujujzk.ee.domain.gateway

import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import kotlinx.coroutines.flow.Flow

interface DictionaryGateway {

    fun observeDictionaries(): Flow<List<Dictionary>>

    //TODO TEMP
    suspend fun testArticleParser(): Article

}