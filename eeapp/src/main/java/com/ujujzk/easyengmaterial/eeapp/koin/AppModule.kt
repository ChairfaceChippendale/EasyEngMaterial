package com.ujujzk.easyengmaterial.eeapp.koin

import com.ujujzk.easyengmaterial.eeapp.tools.Looog
import com.ujujzk.ee.catalog.di.catalogModule
import com.ujujzk.ee.data.di.LOG_DATA
import com.ujujzk.ee.data.di.LOG_DATA_EX
import com.ujujzk.ee.data.di.dataModule
import com.ujujzk.ee.dictionary.di.dictionaryModule
import com.ujujzk.ee.domain.di.domainModule
import com.ujujzk.ee.learn.di.learnModule
import com.ujujzk.ee.local.dictionary.di.dicLocalModule
import com.ujujzk.ee.pack.di.packModule
import com.ujujzk.ee.presentation.di.LOG_UI
import com.ujujzk.ee.presentation.di.navigationModule
import com.ujujzk.ee.presentation.di.presentationModule
import com.ujujzk.ee.store.di.storeModule
import com.ujujzk.ee.translate.di.translateModule
import com.ujujzk.ee.vocabulary.di.vocabularyModule
import com.ujujzk.main.di.mainModule
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModules: List<Module>
    get() = listOf(
        coreModule,

        domainModule,

        dataModule,
        dicLocalModule,

        navigationModule,

        presentationModule,
        mainModule,
        dictionaryModule,
        catalogModule,
        translateModule,

        vocabularyModule,
        packModule,
        storeModule,
        learnModule
    )

val coreModule = module {
    
//    single(named(LOG_NET)){ Looog.i(tag = "NET")}
//    single(named(LOG_ROOM)){ Looog.d(tag = "ROOM")}
    single(named(LOG_DATA)) { Looog.d(tag = "DATA") }
    single(named(LOG_DATA_EX)) { Looog.ex(tag = "DATA") }
//    single(named(LOG_STORAGE_EX)){ Looog.ex(tag = "STORE")}
//    single(named(LOG_PREF_EX)){ Looog.th(tag = "STORE")}
    single(named(LOG_UI)) { Looog.i(tag = "UI") }

}