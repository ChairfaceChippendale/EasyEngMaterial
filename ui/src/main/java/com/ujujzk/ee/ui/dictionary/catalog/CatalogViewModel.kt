package com.ujujzk.ee.ui.dictionary.catalog

import com.ujujzk.ee.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

class CatalogViewModel(
    disposables: CompositeDisposable,
    private val logger: (String) -> Unit,
    private val router: Router
): BaseViewModel(disposables) {

    fun onBackPressed() : Boolean {
        router.exit()
        return true
    }
}