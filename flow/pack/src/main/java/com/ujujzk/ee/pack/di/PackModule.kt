package com.ujujzk.ee.pack.di

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ujujzk.ee.pack.PackFragment
import com.ujujzk.ee.pack.PackViewModel
import com.ujujzk.ee.presentation.di.KOIN_NAV_VOC_ROUTER
import com.ujujzk.ee.presentation.di.LOG_UI
import com.ujujzk.ee.presentation.di.SCREEN_PACK
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val packModule = module {

    viewModel {
        PackViewModel(
            logger = get(named(LOG_UI)),
            router = get(named(KOIN_NAV_VOC_ROUTER))
        )
    }

    factory(named(SCREEN_PACK)) {
        FragmentScreen(SCREEN_PACK) { PackFragment() }
    }
}