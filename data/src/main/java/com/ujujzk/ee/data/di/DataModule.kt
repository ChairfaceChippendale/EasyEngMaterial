package com.ujujzk.ee.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ujujzk.ee.data.JobExecutor
import com.ujujzk.ee.data.gateway.dic.DicGatewayImpl
import com.ujujzk.ee.data.gateway.voc.VocGatewayImpl
import com.ujujzk.ee.domain.di.IO_CONTEXT
import com.ujujzk.ee.domain.executor.ThreadExecutor
import com.ujujzk.ee.domain.gateway.DictionaryGateway
import com.ujujzk.ee.domain.gateway.VocabularyGateway
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext


const val LOG_DATA = "LOG_DATA"
const val LOG_DATA_EX = "LOG_DATA_EX"

const val RETROFIT_GSON = "RETROFIT_GSON"
const val COMMON_GSON = "COMMON_GSON"


val dataModule = module {

    single<CoroutineContext>(named(IO_CONTEXT)) { JobExecutor().asCoroutineDispatcher() }

//    single<ThreadExecutor> { JobExecutor() }

    single(named(COMMON_GSON)) { Gson() }

    single<Gson>(named(RETROFIT_GSON)) {
        GsonBuilder()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
    }

    single<DictionaryGateway> {
        DicGatewayImpl(
            dicLocal =  get()
        )
    }

    single<VocabularyGateway> {
        VocGatewayImpl(

        )
    }

}
