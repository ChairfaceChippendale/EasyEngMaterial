package com.ujujzk.ee.domain.di

import com.ujujzk.ee.domain.exceptions.ErrorConverter
import com.ujujzk.ee.domain.usecase.dic.ObserveDictionariesUseCase
import com.ujujzk.ee.domain.usecase.dic.TestArticleDefinitionUseCase
import com.ujujzk.ee.domain.usecase.voc.GetPackUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO_CONTEXT = "IO_CONTEXT"

val domainModule = module {

    single {
        ErrorConverter(
            setOf(
                //get(named(ERROR_MAPPER_NETWORK))
            )
        )
    }

    factory {
        ObserveDictionariesUseCase(
            executionContext = get(named(IO_CONTEXT)),
            errorConverter = get(),
            dicGateway = get()
        )
    }

    factory {
        GetPackUseCase(
            executionContext = get(named(IO_CONTEXT)),
            errorConverter = get(),
            vocGateway = get()
        )
    }

    //TODO TEMP
    factory {
        TestArticleDefinitionUseCase(
            executionContext = get(named(IO_CONTEXT)),
            errorConverter = get(),
            dicGateway = get()
        )
    }


}