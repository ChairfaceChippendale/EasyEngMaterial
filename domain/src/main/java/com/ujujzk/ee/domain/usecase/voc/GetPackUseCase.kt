package com.ujujzk.ee.domain.usecase.voc

import com.ujujzk.ee.domain.executor.PostExecutionThread
import com.ujujzk.ee.domain.executor.ThreadExecutor
import com.ujujzk.ee.domain.gateway.VocGateway
import com.ujujzk.ee.domain.usecase.UseCaseCompletable
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable

class GetPackUseCase(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    disposable: CompositeDisposable,
    private val vocGateway: VocGateway
): UseCaseCompletable<GetPackUseCase.Params>(threadExecutor, postExecutionThread, disposable) {


    override fun buildUseCase(params: Params): Completable {
        return vocGateway.testVoc()
    }

    class Params {
        companion object {
            fun get() = Params()
        }
    }
}