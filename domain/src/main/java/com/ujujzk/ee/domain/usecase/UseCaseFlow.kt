package com.ujujzk.ee.domain.usecase

import com.ujujzk.ee.domain.exceptions.ErrorConverter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

typealias FlowBlock<T> = UseCaseFlow.Request<T>.() -> Unit

abstract class UseCaseFlow<T, in Params>(
    private val executionContext: CoroutineContext,
    private val errorConverter: ErrorConverter
) {

    protected abstract suspend fun executeOnBackground(params: Params): Flow<T>

    suspend fun execute(params: Params, block: FlowBlock<T>) {

        val response = Request<T>().apply(block)

        executeOnBackground(params)
            .flowOn(executionContext)
            .onEach { response.onNext.invoke(it) }
            .catch { e ->
                if (e is CancellationException) {
                    response.onCancel?.invoke(e)
                } else {
                    response.onError?.invoke(errorConverter.convertError(e))
                }
            }
            .collect()
    }

    class Request<T> {
        lateinit var onNext: (T) -> Unit
        var onError: ((Throwable) -> Unit)? = null
        var onCancel: ((CancellationException) -> Unit)? = null
    }
}