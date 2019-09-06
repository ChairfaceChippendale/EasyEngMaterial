package com.ujujzk.ee.ui.vocabulary.pack

import com.ujujzk.ee.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router


class PackViewModel(
    disposables: CompositeDisposable,
    private val logger: (String) -> Unit,
    private val router: Router
): BaseViewModel(disposables) {

    fun onBackPressed() : Boolean {
        router.exit()
        return true
    }
}