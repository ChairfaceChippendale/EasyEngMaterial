package com.ujujzk.ee.vocabulary.di

import com.ujujzk.ee.presentation.di.PARENT_VOCABULARY
import com.ujujzk.ee.presentation.navigation.FlowFragment
import com.ujujzk.ee.vocabulary.VocabularyParent
import org.koin.core.qualifier.named
import org.koin.dsl.module

val vocabularyModule = module {

    factory<FlowFragment>(named(PARENT_VOCABULARY)) { VocabularyParent() }
}