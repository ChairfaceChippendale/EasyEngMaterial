package com.ujujzk.ee.data.di

import androidx.room.Room
import com.google.gson.Gson
import com.ujujzk.ee.data.AppDatabase
import com.ujujzk.ee.data.JobExecutor
import com.ujujzk.ee.data.interceptor.LoggingInterceptor
import com.ujujzk.ee.data.interceptor.MockInterceptor
import com.ujujzk.ee.data.source.dic.DicGatewayImpl
import com.ujujzk.ee.data.source.dic.DicStorage
import com.ujujzk.ee.data.source.dic.local.DicStorageFake
import com.ujujzk.ee.data.source.dic.local.DicStorageRoom
import com.ujujzk.ee.data.source.dic.local.model.*
import com.ujujzk.ee.data.source.voc.VocGatewayImpl
import com.ujujzk.ee.data.source.voc.VocRemote
import com.ujujzk.ee.data.source.voc.remote.VocApi
import com.ujujzk.ee.data.source.voc.remote.VocRemoteRest
import com.ujujzk.ee.data.tools.mapper.MapperDelegate
import com.ujujzk.ee.domain.executor.ThreadExecutor
import com.ujujzk.ee.domain.gateway.DicGateway
import com.ujujzk.ee.domain.gateway.VocGateway
import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


const val LOG_DATA = "LOG_DATA"
const val LOG_ROOM = "LOG_ROOM"
const val LOG_NET = "LOG_NET"
const val LOG_DATA_EX = "LOG_DATA_EX"
const val LOG_STORAGE_EX = "LOG_STORAGE_EX"
const val LOG_PREF_EX = "LOG_PREF_EX"

const val DB_NAME = "easy_eng_db"

val dataModule = module {

    single<ThreadExecutor> { JobExecutor() }

    single(named("gson")) { Gson() }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single<DicGateway> { DicGatewayImpl(get(), get()) }
//    single<DicStorage> { DicStorageRoom(get(), get(), get()) }
    single<DicStorage> { DicStorageFake() }
    single { get<AppDatabase>().getDictionaryDao() }
    single { get<AppDatabase>().getArticleDao() }

    single<VocGateway> { VocGatewayImpl(get()) }
    single<VocRemote> { VocRemoteRest(get()) }


    single {
        MapperDelegate.Builder()
            .registerConverter(
                object : MapperDelegate.TypeRef<DictionaryRoom>() {},
                object : MapperDelegate.TypeRef<Dictionary>() {},
                DictionaryFromRoomToDomain
            )
            .registerConverter(
                object : MapperDelegate.TypeRef<List<DictionaryRoom>>() {},
                object : MapperDelegate.TypeRef<List<Dictionary>>() {},
                DictionariesFromRoomToDomain
            )
            .registerConverter(
                object : MapperDelegate.TypeRef<ArticleRoom>() {},
                object : MapperDelegate.TypeRef<Article>() {},
                ArticleFromRoomToDomain
            )
            .registerConverter(
                object : MapperDelegate.TypeRef<List<ArticleRoom>>() {},
                object : MapperDelegate.TypeRef<List<Article>>() {},
                ArticlesFromRoomToDomain
            )
            .build()
    }

    single<VocApi> { get<Retrofit>().create(VocApi::class.java) }

    single {
        Retrofit
            .Builder()
            .client(get<OkHttpClient>())
            .baseUrl("https://ujujzk.com/")
            .addConverterFactory(GsonConverterFactory.create(get(named("gson"))))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single<OkHttpClient> {

        val level =
            if (getProperty("debug")) LoggingInterceptor.Level.HEADERS_BODY else LoggingInterceptor.Level.NONE

        OkHttpClient
            .Builder()
            .addInterceptor(LoggingInterceptor(level, get(named(LOG_NET)), true).apply {
                redactHeader("header-two")
                redactHeader("Refresh-token")
            })
            .addInterceptor(get<MockInterceptor>()) //must be the last
            .build()
    }

    single { MockInterceptor(getProperty("debug")) }

}
