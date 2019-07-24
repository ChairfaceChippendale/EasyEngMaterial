package com.ujujzk.ee.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ujujzk.ee.data.source.dic.local.DictionaryDao
import com.ujujzk.ee.data.source.dic.local.model.DictionaryRoom

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        DictionaryRoom::class
    ])
abstract class AppDatabase: RoomDatabase() {

    abstract fun getDictionaryDao(): DictionaryDao

}