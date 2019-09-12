package com.ujujzk.ee.data.source.dic.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ujujzk.ee.data.source.dic.local.model.ArticleRoom
import io.reactivex.Maybe

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: ArticleRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<ArticleRoom>)

    @Query("SELECT * FROM articleroom WHERE wordName LIKE '%' || :query || '%' ORDER BY wordName LIMIT 100")
    fun searchBy(query: String): Maybe<List<ArticleRoom>>

    @Query("DELETE FROM articleroom WHERE dictionaryId == :dictionaryId")
    fun deleteByDictionary(dictionaryId: String)

}