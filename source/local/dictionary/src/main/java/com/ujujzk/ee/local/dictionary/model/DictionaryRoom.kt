package com.ujujzk.ee.local.dictionary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DictionaryRoom (
    @PrimaryKey
    val id: String,
    val name: String
)