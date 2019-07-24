package com.ujujzk.ee.data.di

import androidx.room.Room
import com.google.gson.Gson
import com.ujujzk.ee.data.AppDatabase
import com.ujujzk.ee.data.JobExecutor
import com.ujujzk.ee.data.source.dic.DicGatewayImpl
import com.ujujzk.ee.data.source.dic.local.DicStorageRoom
import com.ujujzk.ee.domain.executor.ThreadExecutor
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


    single { DicGatewayImpl(get()) }
    single { DicStorageRoom(get()) }
    single { get<AppDatabase>().getDictionaryDao() }

}
