package com.ujujzk.domain.model

data class Card (
        val id: String,
        val question: String,
        val answer: String,
        val image: String = "")

data class Pack (
        val id: String,
        val name: String,
        val cards: List<Card>
)