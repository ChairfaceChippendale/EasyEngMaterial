package com.ujujzk.ee.domain.di

import com.ujujzk.ee.domain.usecase.dic.ObserveDictionariesUseCase
import com.ujujzk.ee.domain.usecase.dic.TestArticleDefinitionUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { ObserveDictionariesUseCase(get(), get(), get(), get()) }

    //TODO TEMP
    factory { TestArticleDefinitionUseCase(get(), get(), get(), get()) }


}