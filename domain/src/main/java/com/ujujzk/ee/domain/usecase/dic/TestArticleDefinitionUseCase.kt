package com.ujujzk.ee.domain.usecase.dic

import com.ujujzk.ee.domain.executor.PostExecutionThread
import com.ujujzk.ee.domain.executor.ThreadExecutor
import com.ujujzk.ee.domain.gateway.DicGateway
import com.ujujzk.ee.domain.usecase.UseCaseFlowable
import com.ujujzk.ee.domain.usecase.UseCaseSingle
import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

//TODO TEMP
class TestArticleDefinitionUseCase(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    disposable: CompositeDisposable,
    private val dicGateway: DicGateway
) : UseCaseSingle<Article, TestArticleDefinitionUseCase.Params>(threadExecutor, postExecutionThread, disposable){

    override fun buildUseCase(params: Params): Single<Article> =
        dicGateway.ddd()


    class Params {
        companion object {
            fun get() = Params()
        }
    }
}