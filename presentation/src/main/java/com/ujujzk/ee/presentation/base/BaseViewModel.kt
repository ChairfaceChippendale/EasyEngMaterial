package com.ujujzk.ee.presentation.base

import androidx.lifecycle.*
import com.ujujzk.ee.domain.exceptions.NetworkError
import com.ujujzk.ee.domain.usecase.CompletionBlock
import com.ujujzk.ee.domain.usecase.FlowBlock
import com.ujujzk.ee.domain.usecase.UseCaseCoroutine
import com.ujujzk.ee.domain.usecase.UseCaseFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel (
    private val logger: (String) -> Unit
): ViewModel() {

    val progress: MutableLiveData<Boolean> = MutableLiveData(false)

    val networkError: SingleLiveEvent<Unit> = SingleLiveEvent()
    val commonError: SingleLiveEvent<Unit> = SingleLiveEvent()

    private val jobs = mutableMapOf<String, Job>()

    abstract fun onBackPressed(): Boolean

    override fun onCleared() {
        jobs.clear()
        super.onCleared()
    }

    fun handleCommonErrors(exception: Throwable) {
        logger(exception.message ?: "Unknown exception")
        when (exception){
            is NetworkError -> { networkError.call() }
//            is CannotUpdateTokenException -> { forceLogout() }
            else -> { commonError.call() }
        }
    }

    fun <T, R, U : UseCaseCoroutine<T, R>>U.launch(param: R, isSingleTask: Boolean = false, block: CompletionBlock<T>): Job {
        if (isSingleTask) {
            logger("Rerun ${this.javaClass.name}")
            if (jobs[this.javaClass.name]?.isCompleted == false) jobs[this.javaClass.name]?.cancel()
            val rerun = viewModelScope.launch { execute(param, block) }
            jobs[this.javaClass.name] = rerun
            return rerun
        } else {
            return viewModelScope.launch { execute(param, block) }
        }
    }

    fun <T, R, U : UseCaseFlow<T, R>>U.launch(param: R, block: FlowBlock<T>) {
        viewModelScope.launch { execute(param, block) }
    }
}