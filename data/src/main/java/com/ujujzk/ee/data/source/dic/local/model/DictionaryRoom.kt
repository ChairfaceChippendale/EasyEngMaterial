package com.ujujzk.ee.data.source.dic.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DictionaryRoom (
    @PrimaryKey
    val id: String,
    val name: String
)