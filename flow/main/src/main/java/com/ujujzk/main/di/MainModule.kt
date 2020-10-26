package com.ujujzk.main.di

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ujujzk.ee.presentation.di.KOIN_NAV_APP_ROUTER
import com.ujujzk.ee.presentation.di.KOIN_NAV_MAIN_ROUTER
import com.ujujzk.ee.presentation.di.LOG_UI
import com.ujujzk.ee.presentation.di.PARENT_MAIN
import com.ujujzk.main.MainParent
import com.ujujzk.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        MainViewModel(
            logger = get(named(LOG_UI)),
            appRouter = get(named(KOIN_NAV_APP_ROUTER)),
            mainRouter = get(named(KOIN_NAV_MAIN_ROUTER))
        )

    }

    factory(named(PARENT_MAIN)) { FragmentScreen(PARENT_MAIN) { MainParent() } }
}