package com.ujujzk.ee.local.dictionary

import com.ujujzk.ee.data.gateway.dic.DicLocal
import com.ujujzk.ee.data.source.dic.local.model.toDomain
import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DicStorageRoom(
    private val dicDao: DictionaryDao,
    private val articleDao: ArticleDao,
): DicLocal  {

     override fun observeDictionaries(): Flow<List<Dictionary>> {

        return dicDao.observeAll().map { it.map { it.toDomain() } }

     }

    override suspend fun testArticleParser(): Article {
        TODO("Not yet implemented")
    }
}