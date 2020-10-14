package com.ujujzk.ee.translate

import com.ujujzk.ee.presentation.base.BaseViewModel
import com.ujujzk.ee.presentation.di.FlowQualifier
import com.ujujzk.ee.presentation.navigation.ScreenFactory
import ru.terrakok.cicerone.Router


class TranslateViewModel(
    private val logger: (String) -> Unit,
    private val router: Router,
    private val screens: ScreenFactory
): BaseViewModel(logger) {

    fun onCatalogClick(){
        router.navigateTo(screens.catalog(FlowQualifier.DICTIONARY))
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}