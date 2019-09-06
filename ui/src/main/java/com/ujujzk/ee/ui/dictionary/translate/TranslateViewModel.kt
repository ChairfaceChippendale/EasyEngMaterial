package com.ujujzk.ee.ui.dictionary.translate

import com.ujujzk.ee.ui.base.BaseViewModel
import com.ujujzk.ee.ui.dictionary.catalog.CatalogFragment
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

class TranslateViewModel(
    disposables: CompositeDisposable,
    private val logger: (String) -> Unit,
    private val router: Router
): BaseViewModel(disposables) {

    fun onCatalogClick(){
        router.navigateTo(CatalogFragment.Screen())
    }

    fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

}