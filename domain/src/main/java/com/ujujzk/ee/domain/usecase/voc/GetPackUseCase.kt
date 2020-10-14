package com.ujujzk.ee.domain.usecase.voc

import com.ujujzk.ee.domain.exceptions.ErrorConverter
import com.ujujzk.ee.domain.gateway.VocabularyGateway
import com.ujujzk.ee.domain.usecase.UseCaseCoroutine
import kotlin.coroutines.CoroutineContext

class GetPackUseCase(
    executionContext: CoroutineContext,
    errorConverter: ErrorConverter,
    private val vocGateway: VocabularyGateway
): UseCaseCoroutine<Unit, GetPackUseCase.Params>(executionContext, errorConverter) {

    override suspend fun executeOnBackground(params: Params) {
        return vocGateway.testVoc()
    }

    class Params {
        companion object {
            fun get() = Params()
        }
    }
}