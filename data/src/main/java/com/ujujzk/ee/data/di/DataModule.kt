package com.ujujzk.ee.data.di

import androidx.room.Room
import com.google.gson.Gson
import com.ujujzk.ee.data.AppDatabase
import com.ujujzk.ee.data.JobExecutor
import com.ujujzk.ee.data.source.dic.DicGatewayImpl
import com.ujujzk.ee.data.source.dic.DicStorage
import com.ujujzk.ee.data.source.dic.local.DicStorageRoom
import com.ujujzk.ee.data.source.dic.local.model.*
import com.ujujzk.ee.data.tools.mapper.MapperDelegate
import com.ujujzk.ee.domain.executor.ThreadExecutor
import com.ujujzk.ee.domain.gateway.DicGateway
import com.ujujzk.ee.domain.usecase.dic.model.Article
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module


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
    single<DicStorage> { DicStorageRoom(get(), get(), get()) }
    single { get<AppDatabase>().getDictionaryDao() }
    single { get<AppDatabase>().getArticleDao() }


    single {
        MapperDelegate.Builder()
            .registerConverter(
                object : MapperDelegate.TypeRef<DictionaryRoom>(){},
                object : MapperDelegate.TypeRef<Dictionary>(){},
                DictionaryFromRoomToDomain
            )
            .registerConverter(
                object : MapperDelegate.TypeRef<List<DictionaryRoom>>(){},
                object : MapperDelegate.TypeRef<List<Dictionary>>(){},
                DictionariesFromRoomToDomain
            )
            .registerConverter(
                object : MapperDelegate.TypeRef<ArticleRoom>(){},
                object : MapperDelegate.TypeRef<Article>(){},
                ArticleFromRoomToDomain
            )
            .registerConverter(
                object : MapperDelegate.TypeRef<List<ArticleRoom>>(){},
                object : MapperDelegate.TypeRef<List<Article>>(){},
                ArticlesFromRoomToDomain
            )
            .build()
    }

}
