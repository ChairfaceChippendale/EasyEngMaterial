package com.ujujzk.ee.local.dictionary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ArticleRoom (
    @PrimaryKey
    val id: String,
    val squareBracketText: String,
    val wordName: String,
    val dictionaryId: String,
    val dictionaryName: String
)