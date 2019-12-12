package com.ujujzk.ee.ui.di

import com.ujujzk.ee.domain.executor.PostExecutionThread
import com.ujujzk.ee.ui.UiThread
import org.koin.dsl.module

const val LOG_UI = "LOG_UI"

val presentationModule = module {

    single<PostExecutionThread> { UiThread() }
}