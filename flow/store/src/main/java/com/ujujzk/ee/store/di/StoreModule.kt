package com.ujujzk.ee.store.di

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ujujzk.ee.presentation.di.KOIN_NAV_VOC_ROUTER
import com.ujujzk.ee.presentation.di.LOG_UI
import com.ujujzk.ee.presentation.di.SCREEN_STORE
import com.ujujzk.ee.store.StoreFragment
import com.ujujzk.ee.store.StoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val storeModule = module {

    viewModel {
        StoreViewModel(
            logger = get(named(LOG_UI)),
            router = get(named(KOIN_NAV_VOC_ROUTER)),
            screens = get()
        )
    }

    factory(named(SCREEN_STORE)) {
        FragmentScreen(SCREEN_STORE) { StoreFragment() }
    }
}