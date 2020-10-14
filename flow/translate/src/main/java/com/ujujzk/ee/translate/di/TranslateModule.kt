package com.ujujzk.ee.translate.di

import com.ujujzk.ee.translate.TranslateViewModel
import com.ujujzk.ee.presentation.di.KOIN_NAV_DIC_ROUTER
import com.ujujzk.ee.presentation.di.LOG_UI
import com.ujujzk.ee.presentation.di.SCREEN_TRANSLATE
import com.ujujzk.ee.presentation.navigation.FragmentScreen
import com.ujujzk.ee.translate.TranslateFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val translateModule = module {

    viewModel {
        TranslateViewModel(
            logger = get(named(LOG_UI)),
            router = get(named(KOIN_NAV_DIC_ROUTER)),
            screens = get()
        )
    }

    factory(named(SCREEN_TRANSLATE)) {
        FragmentScreen { TranslateFragment() }
    }

}