package com.ujujzk.ee.presentation.di

import com.ujujzk.ee.presentation.navigation.ScreenFactory
import org.koin.dsl.module

const val LOG_UI = "LOG_UI"

val presentationModule = module {

    single {
        ScreenFactory()
    }
}