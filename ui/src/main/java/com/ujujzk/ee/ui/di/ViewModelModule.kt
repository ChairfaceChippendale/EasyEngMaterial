package com.ujujzk.ee.ui.di

import com.ujujzk.ee.ui.dictionary.catalog.CatalogViewModel
import com.ujujzk.ee.ui.dictionary.translate.TranslateViewModel
import com.ujujzk.ee.ui.vocabulary.learn.LearnViewModel
import com.ujujzk.ee.ui.vocabulary.pack.PackViewModel
import com.ujujzk.ee.ui.vocabulary.store.StoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val LOG_UI = "LOG_UI"

val viewModelModule = module {

    viewModel { TranslateViewModel(get(), get(named(LOG_UI)), get(named(KOIN_NAV_DIC_ROUTER))) }

    viewModel { StoreViewModel(get(), get(named(LOG_UI)), get(named(KOIN_NAV_VOC_ROUTER))) }
    viewModel { LearnViewModel(get(), get(named(LOG_UI)), get(named(KOIN_NAV_VOC_ROUTER))) }
    viewModel { PackViewModel(get(), get(named(LOG_UI)), get(named(KOIN_NAV_VOC_ROUTER))) }

    viewModel { (param: Flow) ->
        CatalogViewModel(
            disposables = get(),
            logger = get(named(LOG_UI)),
            router = when (param) {
                Flow.DICTIONARY -> get(named(KOIN_NAV_DIC_ROUTER))
                Flow.VOCABULARY -> get(named(KOIN_NAV_VOC_ROUTER))
            },
            observeDictionariesUseCase = get(),
            test = get()
        )
    }

}



