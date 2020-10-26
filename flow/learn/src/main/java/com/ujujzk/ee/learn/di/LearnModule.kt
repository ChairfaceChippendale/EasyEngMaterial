package com.ujujzk.ee.learn.di

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ujujzk.ee.learn.LearnFragment
import com.ujujzk.ee.learn.LearnViewModel
import com.ujujzk.ee.presentation.di.KOIN_NAV_VOC_ROUTER
import com.ujujzk.ee.presentation.di.LOG_UI
import com.ujujzk.ee.presentation.di.SCREEN_LEARN
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val learnModule = module {

    viewModel {
        LearnViewModel(
            logger = get(named(LOG_UI)),
            router = get(named(KOIN_NAV_VOC_ROUTER))
        )
    }

    factory(named(SCREEN_LEARN)) {
        FragmentScreen(SCREEN_LEARN) {
            LearnFragment()
        }
    }
}