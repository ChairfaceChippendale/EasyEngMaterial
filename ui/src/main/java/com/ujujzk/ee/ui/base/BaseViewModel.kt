package com.ujujzk.ee.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel (
    private val disposable: CompositeDisposable
): ViewModel() {


    protected fun addDisposable(subscription: Disposable) {
        disposable.add(subscription)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }


}