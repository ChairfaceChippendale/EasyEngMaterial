package com.ujujzk.easyengmaterial.eeapp.koin

import com.ujujzk.easyengmaterial.eeapp.tools.Looog
import com.ujujzk.ee.data.di.*
import com.ujujzk.ee.domain.di.domainModule
import com.ujujzk.ee.ui.di.LOG_UI
import com.ujujzk.ee.ui.di.navigationModule
import com.ujujzk.ee.ui.di.presentationModule
import com.ujujzk.ee.ui.di.viewModelModule
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModules: List<Module>
    get() = listOf(
        coreModule,

        domainModule,

        dataModule,

        navigationModule,

        viewModelModule,
        presentationModule
    )

val coreModule = module {

    factory { CompositeDisposable() }

    single(named(LOG_NET)){ Looog.i(tag = "NET")}
    single(named(LOG_ROOM)){ Looog.d(tag = "ROOM")}
    single(named(LOG_DATA)){ Looog.d(tag = "DATA")}
    single(named(LOG_DATA_EX)){ Looog.ex(tag = "DATA")}
    single(named(LOG_STORAGE_EX)){ Looog.ex(tag = "STORE")}
    single(named(LOG_PREF_EX)){ Looog.th(tag = "STORE")}
    single(named(LOG_UI)){ Looog.i(tag = "UI")}

}