package com.ujujzk.ee.data.source.dic.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ujujzk.ee.data.source.dic.local.model.DictionaryRoom
import io.reactivex.Flowable

@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dic: DictionaryRoom)

    @Query("SELECT * FROM dictionaryroom")
    fun observeAll(): Flowable<List<DictionaryRoom>>

    @Query("DELETE FROM dictionaryroom WHERE id == :id")
    fun delete(id: String)

}