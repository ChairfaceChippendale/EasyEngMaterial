package com.ujujzk.ee.presentation.model

import androidx.annotation.IntRange


data class VDictionary(
    val id: String,
    val dictionaryName: String,
    val enabledForSearch: Boolean = true,
    @IntRange(from = 0, to = 100)
    val installationProgress: Int = 100
)
