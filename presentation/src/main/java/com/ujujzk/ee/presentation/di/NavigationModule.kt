package com.ujujzk.ee.presentation.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.ujujzk.ee.presentation.navigation.switchnav.SwitchRouter
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val PARENT_MAIN = "PARENT_MAIN"

const val PARENT_DICTIONARY = "PARENT_DICTIONARY"
const val SCREEN_TRANSLATE = "SCREEN_TRANSLATE"
const val SCREEN_CATALOG = "SCREEN_CATALOG"

const val PARENT_VOCABULARY = "PARENT_VOCABULARY"
const val SCREEN_LEARN = "SCREEN_LEARN"
const val SCREEN_PACK = "SCREEN_PACK"
const val SCREEN_STORE = "SCREEN_STORE"

const val DICTIONARY_TAG_TAB = "DICTIONARY_TAG_TAB"
const val VOCABULARY_TAG_TAB = "VOCABULARY_TAG_TAB"
const val GRAMMAR_TAG_TAB = "GRAMMAR_TAG_TAB"

const val KOIN_NAV_APP_ROUTER = "KOIN_NAV_APP_ROUTER"
const val KOIN_NAV_APP_CICERONE = "KOIN_NAV_APP_CICERONE"

const val KOIN_NAV_MAIN_ROUTER = "KOIN_NAV_MAIN_ROUTER"
const val KOIN_NAV_MAIN_CICERONE = "KOIN_NAV_MAIN_CICERONE"

const val KOIN_NAV_DIC_ROUTER = "KOIN_NAV_DIC_ROUTER"
const val KOIN_NAV_DIC_CICERONE = "KOIN_NAV_DIC_CICERONE"

const val KOIN_NAV_VOC_ROUTER  = "KOIN_NAV_VOC_ROUTER"
const val KOIN_NAV_VOC_CICERONE  = "KOIN_NAV_VOC_CICERONE"

enum class FlowQualifier {
    DICTIONARY,
    VOCABULARY
}

val navigationModule = module {

    single<Cicerone<Router>>(named(KOIN_NAV_APP_CICERONE)){ Cicerone.create() }
    single<Router>(named(KOIN_NAV_APP_ROUTER)){
        get<Cicerone<Router>>(named(KOIN_NAV_APP_CICERONE)).router
    }

    single<Cicerone<SwitchRouter>>(named(KOIN_NAV_MAIN_CICERONE)) { Cicerone.create(SwitchRouter()) }
    single<SwitchRouter>(named(KOIN_NAV_MAIN_ROUTER)) {
        get<Cicerone<SwitchRouter>>(named(KOIN_NAV_MAIN_CICERONE)).router
    }

    single<Cicerone<Router>>(named(KOIN_NAV_DIC_CICERONE)){ Cicerone.create() }
    single<Router>(named(KOIN_NAV_DIC_ROUTER)){
        get<Cicerone<Router>>(named(KOIN_NAV_DIC_CICERONE)).router
    }

    single<Cicerone<Router>>(named(KOIN_NAV_VOC_CICERONE)){ Cicerone.create() }
    single<Router>(named(KOIN_NAV_VOC_ROUTER)){
        get<Cicerone<Router>>(named(KOIN_NAV_VOC_CICERONE)).router
    }
}
