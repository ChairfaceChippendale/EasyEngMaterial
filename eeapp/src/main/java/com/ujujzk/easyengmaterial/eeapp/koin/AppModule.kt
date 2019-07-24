package com.ujujzk.easyengmaterial.eeapp.koin

import com.ujujzk.easyengmaterial.eeapp.tools.Looog
import com.ujujzk.ee.data.di.dataModule
import com.ujujzk.ee.domain.di.domainModule
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModules: List<Module>
    get() = listOf(
        coreModule,

        domainModule,

        dataModule/*,

        viewModelModule,
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

}