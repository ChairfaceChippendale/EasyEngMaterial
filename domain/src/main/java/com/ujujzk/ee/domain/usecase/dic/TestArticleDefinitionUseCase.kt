package com.ujujzk.ee.domain.usecase.dic

import com.ujujzk.ee.domain.exceptions.ErrorConverter
import com.ujujzk.ee.domain.gateway.DictionaryGateway
import com.ujujzk.ee.domain.usecase.UseCaseCoroutine
import com.ujujzk.ee.domain.usecase.dic.model.Article
import kotlin.coroutines.CoroutineContext

//TODO TEMP
class TestArticleDefinitionUseCase(
    executionContext: CoroutineContext,
    errorConverter: ErrorConverter,
    private val dicGateway: DictionaryGateway
) : UseCaseCoroutine<Article, TestArticleDefinitionUseCase.Params>(executionContext, errorConverter){

    override suspend fun executeOnBackground(params: Params): Article =
        dicGateway.testArticleParser()


    class Params {
        companion object {
            fun get() = Params()
        }
    }
}