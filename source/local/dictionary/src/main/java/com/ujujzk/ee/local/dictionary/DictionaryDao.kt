package com.ujujzk.ee.local.dictionary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ujujzk.ee.local.dictionary.model.DictionaryRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dic: DictionaryRoom)

    @Query("SELECT * FROM dictionaryroom")
    fun observeAll(): Flow<List<DictionaryRoom>>

    @Query("DELETE FROM dictionaryroom WHERE id == :id")
    fun delete(id: String)

}