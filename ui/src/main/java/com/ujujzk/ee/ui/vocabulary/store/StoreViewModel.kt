package com.ujujzk.ee.ui.vocabulary.store

import com.ujujzk.ee.ui.base.BaseViewModel
import com.ujujzk.ee.ui.di.Flow
import com.ujujzk.ee.ui.dictionary.catalog.CatalogFragment
import com.ujujzk.ee.ui.vocabulary.learn.LearnFragment
import com.ujujzk.ee.ui.vocabulary.pack.PackFragment
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

class StoreViewModel(
    disposables: CompositeDisposable,
    private val logger: (String) -> Unit,
    private val router: Router
): BaseViewModel(disposables) {

    fun onLearnClick(){
        router.navigateTo(LearnFragment.Screen())
    }

    fun onEditPackClick(){
        router.navigateTo(PackFragment.Screen())
    }

    fun onCatalogClick(){
        router.navigateTo(CatalogFragment.Screen(Flow.VOCABULARY))
    }

    fun onBackPressed() : Boolean {
        router.exit()
        return true
    }
}