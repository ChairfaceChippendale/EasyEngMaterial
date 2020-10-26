package com.ujujzk.ee.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ujujzk.ee.presentation.di.*
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ScreenFactory: KoinComponent {

    fun mainParent(): FragmentScreen =
        get(named(PARENT_MAIN))

    fun dicParent(): FragmentScreen =
        get(named(PARENT_DICTIONARY))

    fun catalog(flowQualifier: FlowQualifier): FragmentScreen =
        get(named(SCREEN_CATALOG)){ parametersOf(flowQualifier) }

    fun translate(): FragmentScreen =
        get(named(SCREEN_TRANSLATE))

    fun vocParent(): FragmentScreen =
        get(named(PARENT_VOCABULARY))

    fun learn(): FragmentScreen =
        get(named(SCREEN_LEARN))

    fun pack(): FragmentScreen =
        get(named(SCREEN_PACK))

    fun store(): FragmentScreen =
        get(named(SCREEN_STORE))
}