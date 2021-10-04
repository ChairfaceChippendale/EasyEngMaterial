package com.ujujzk.ee.dictionary.di

import com.ujujzk.ee.dictionary.DictionaryParent
import com.ujujzk.ee.presentation.di.PARENT_DICTIONARY
import com.ujujzk.ee.presentation.navigation.FlowFragment
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dictionaryModule = module {

    factory<FlowFragment> (named(PARENT_DICTIONARY)){ DictionaryParent() }
}