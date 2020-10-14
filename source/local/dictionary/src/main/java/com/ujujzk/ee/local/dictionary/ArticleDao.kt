package com.ujujzk.ee.local.dictionary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ujujzk.ee.local.dictionary.model.ArticleRoom

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleRoom>)

    @Query("SELECT * FROM articleroom WHERE wordName LIKE '%' || :query || '%' ORDER BY wordName LIMIT 100")
    suspend fun searchBy(query: String): List<ArticleRoom>

    @Query("DELETE FROM articleroom WHERE dictionaryId == :dictionaryId")
    suspend fun deleteByDictionary(dictionaryId: String)

}