package com.ujujzk.ee.dictionary.di

import com.ujujzk.ee.dictionary.DictionaryParent
import com.ujujzk.ee.presentation.di.PARENT_DICTIONARY
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dictionaryModule = module {

    factory(named(PARENT_DICTIONARY)) { DictionaryParent() }
}