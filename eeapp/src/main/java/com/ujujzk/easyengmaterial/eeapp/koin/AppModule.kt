package com.ujujzk.easyengmaterial.eeapp.koin

import com.ujujzk.easyengmaterial.eeapp.tools.Looog
import com.ujujzk.ee.data.di.dataModule
import com.ujujzk.ee.domain.di.domainModule
import com.ujujzk.ee.ui.di.LOG_UI
import com.ujujzk.ee.ui.di.navigationModule
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

        viewModelModule/*,
        presentationModule*/
    )

val coreModule = module {

    factory { CompositeDisposable() }

    single(named("log_net")){ Looog.i(tag = "NET")}
    single(named("log_room")){ Looog.d(tag = "ROOM")}
    single(named("log_data")){ Looog.d(tag = "DATA")}
    single(named("log_ex_data")){ Looog.ex(tag = "DATA")}
    single(named("log_ex_storage")){ Looog.ex(tag = "STORE")}
    single(named("log_ex_pref")){ Looog.th(tag = "STORE")}
    single(named(LOG_UI)){ Looog.i(tag = "UI")}

}