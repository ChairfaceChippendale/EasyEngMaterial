package com.ujujzk.ee.store

import com.github.terrakok.cicerone.Router
import com.ujujzk.ee.presentation.base.BaseViewModel
import com.ujujzk.ee.presentation.di.FlowQualifier
import com.ujujzk.ee.presentation.navigation.ScreenFactory

class StoreViewModel(
    private val logger: (String) -> Unit,
    private val router: Router,
    private val screens: ScreenFactory
): BaseViewModel(logger) {

    fun onLearnClick(){
        router.navigateTo(screens.learn())
    }

    fun onEditPackClick(){
        router.navigateTo(screens.pack())
    }

    fun onCatalogClick(){
        router.navigateTo(screens.catalog(FlowQualifier.VOCABULARY))
    }

    override fun onBackPressed() : Boolean {
        router.exit()
        return true
    }
}