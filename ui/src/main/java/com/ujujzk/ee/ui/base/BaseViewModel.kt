package com.ujujzk.ee.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel (
    val disposable: CompositeDisposable
): ViewModel() {

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

}