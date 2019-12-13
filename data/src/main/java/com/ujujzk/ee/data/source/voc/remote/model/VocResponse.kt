package com.ujujzk.ee.data.source.voc.remote.model

import com.google.gson.annotations.SerializedName

data class VocResponse (
    @SerializedName("packs") val packs: List<PackResponse>
)

data class CardResponse (
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String

)

data class PackResponse (
    @SerializedName("title") val title: String,
    @SerializedName("cards") val cards: List<CardResponse>
)