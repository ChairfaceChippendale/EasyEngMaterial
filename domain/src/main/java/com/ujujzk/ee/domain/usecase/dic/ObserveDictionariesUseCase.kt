package com.ujujzk.ee.domain.usecase.dic

import com.ujujzk.ee.domain.exceptions.ErrorConverter
import com.ujujzk.ee.domain.gateway.DictionaryGateway
import com.ujujzk.ee.domain.usecase.UseCaseFlow
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext


class ObserveDictionariesUseCase(
    executionContext: CoroutineContext,
    errorConverter: ErrorConverter,
    private val dicGateway: DictionaryGateway
) : UseCaseFlow<List<Dictionary>, ObserveDictionariesUseCase.Params>(executionContext, errorConverter){

    override suspend fun executeOnBackground(params: Params): Flow<List<Dictionary>> =
        dicGateway.observeDictionaries()


    class Params {
        companion object {
            fun get() = Params()
        }
    }
}