package com.ujujzk.ee.data.gateway.dic

import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import kotlinx.coroutines.flow.Flow

interface DicLocal {

    fun observeDictionaries(): Flow<List<Dictionary>>

    //TODO TEMP
    suspend fun testArticleParser(): Article
}