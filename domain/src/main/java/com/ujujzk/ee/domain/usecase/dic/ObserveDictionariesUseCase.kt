package com.ujujzk.ee.domain.usecase.dic

import com.ujujzk.ee.domain.executor.PostExecutionThread
import com.ujujzk.ee.domain.executor.ThreadExecutor
import com.ujujzk.ee.domain.gateway.DicGateway
import com.ujujzk.ee.domain.usecase.UseCaseFlowable
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

class ObserveDictionariesUseCase(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    disposable: CompositeDisposable,
    private val dicGateway: DicGateway
) : UseCaseFlowable<List<Dictionary>, ObserveDictionariesUseCase.Params>(threadExecutor, postExecutionThread, disposable){

    override fun buildUseCase(params: Params): Flowable<List<Dictionary>> =
        dicGateway.observeDictionaries()


    class Params {
        companion object {
            fun get() = Params()
        }
    }
}