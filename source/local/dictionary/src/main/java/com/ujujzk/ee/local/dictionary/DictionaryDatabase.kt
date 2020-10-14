package com.ujujzk.ee.local.dictionary

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ujujzk.ee.local.dictionary.model.ArticleRoom
import com.ujujzk.ee.local.dictionary.model.DictionaryRoom


@Database(
    version = 1,
    exportSchema = false,
    entities = [
        DictionaryRoom::class,
        ArticleRoom::class
    ])
abstract class DictionaryDatabase: RoomDatabase() {

    abstract fun getDictionaryDao(): DictionaryDao

    abstract fun getArticleDao(): ArticleDao

}