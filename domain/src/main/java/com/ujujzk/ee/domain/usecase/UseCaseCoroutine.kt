package com.ujujzk.ee.domain.usecase

import com.ujujzk.ee.domain.exceptions.ErrorConverter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = UseCaseCoroutine.Request<T>.() -> Unit

abstract class UseCaseCoroutine<T, in Params>(
    private val executionContext: CoroutineContext,
    private val errorConverter: ErrorConverter
) {

    protected abstract suspend fun executeOnBackground(params: Params): T

    suspend fun execute(params: Params, block: CompletionBlock<T>) {

        val response = Request<T>().apply(block).also { it.onStart?.invoke() }

        try {
            val result = withContext(executionContext) {
                executeOnBackground(params)
            }
            response.onComplete(result)
        } catch (cancellationException: CancellationException) {
            response.onCancel?.invoke(cancellationException)
        } catch (e: Throwable) {
            response.onError?.invoke(errorConverter.convertError(e))
        } finally {
            response.onTerminate?.invoke()
        }
    }

    class Request<T> {
        lateinit var onComplete: (T) -> Unit
        var onError: ((Throwable) -> Unit)? = null
        var onCancel: ((CancellationException) -> Unit)? = null
        var onStart: (() -> Unit)? = null
        var onTerminate: (() -> Unit)? = null
    }
}