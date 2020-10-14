package com.ujujzk.ee.local.dictionary.di

import androidx.room.Room
import com.ujujzk.ee.data.gateway.dic.DicLocal
import com.ujujzk.ee.local.dictionary.DicStorageFake
import com.ujujzk.ee.local.dictionary.DicStorageRoom
import com.ujujzk.ee.local.dictionary.DictionaryDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

const val DICTIONARY_DATABASE_NAME = "easy-english-dictionary-database"


val dicLocalModule = module {

    factory { get<DictionaryDatabase>().getArticleDao() }

    factory { get<DictionaryDatabase>().getDictionaryDao() }

    single {
        Room.databaseBuilder(androidApplication(), DictionaryDatabase::class.java, DICTIONARY_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single<DicLocal> {
        DicStorageFake()
    }

//    single<DicLocal> {
//        DicStorageRoom(
//            dicDao = get(),
//            articleDao = get()
//        )
//    }
}