package com.ujujzk.ee.domain.usecase

import com.ujujzk.ee.domain.executor.PostExecutionThread
import com.ujujzk.ee.domain.executor.ThreadExecutor
import io.reactivex.Maybe
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers

abstract class UseCaseMaybe<T, in Params>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread,
    disposable: CompositeDisposable
): Disposable(disposable) {

    abstract fun buildUseCase(@NonNull params: Params): Maybe<T>

    fun execute(@NonNull observer: DisposableMaybeObserver<T>, @NonNull params: Params) {

        val observable = this.buildUseCase(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)

        addDisposable(observable.subscribeWith(observer))
    }

    fun executeBy(@NonNull params: Params,
                  onSuccess: (t: T) -> Unit,
                  onError: (e: Throwable) -> Unit,
                  onComplete: () -> Unit) {

        val observable = this.buildUseCase(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)

        addDisposable(observable.subscribeWith(object : DisposableMaybeObserver<T>(){
            override fun onSuccess(t: T) = onSuccess(t)
            override fun onComplete() = onComplete()
            override fun onError(e: Throwable) = onError(e)
        }))
    }
}