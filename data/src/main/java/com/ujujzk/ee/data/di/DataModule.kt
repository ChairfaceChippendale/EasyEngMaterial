package com.ujujzk.ee.data.di

import androidx.room.Room
import com.google.gson.Gson
import com.ujujzk.ee.data.AppDatabase
import com.ujujzk.ee.data.JobExecutor
import com.ujujzk.ee.data.source.dic.DicGatewayImpl
import com.ujujzk.ee.data.source.dic.DicStorage
import com.ujujzk.ee.data.source.dic.local.DicStorageRoom
import com.ujujzk.ee.data.source.dic.local.model.DictionariesFromRoomToDomain
import com.ujujzk.ee.data.source.dic.local.model.DictionaryFromRoomToDomain
import com.ujujzk.ee.data.source.dic.local.model.DictionaryRoom
import com.ujujzk.ee.data.tools.mapper.MapperDelegate
import com.ujujzk.ee.domain.executor.ThreadExecutor
import com.ujujzk.ee.domain.gateway.DicGateway
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single<ThreadExecutor> { JobExecutor() }

    single(named("gson")) { Gson() }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "stats_db")
            .fallbackToDestructiveMigration()
            .build()
    }


    single<DicGateway> { DicGatewayImpl(get()) }
    single<DicStorage> { DicStorageRoom(get(), get()) }
    single { get<AppDatabase>().getDictionaryDao() }


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
            .build()
    }

}
