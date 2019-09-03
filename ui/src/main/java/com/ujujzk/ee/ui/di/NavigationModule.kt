package com.ujujzk.ee.ui.di

import com.ujujzk.ee.ui.navigation.SwitchRouter
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

const val KOIN_NAV_MAIN_ROUTER = "KOIN_NAV_MAIN_ROUTER"
const val KOIN_NAV_MAIN_CICERONE = "KOIN_NAV_MAIN_CICERONE"

const val KOIN_NAV_DIC_ROUTER = "KOIN_NAV_DIC_ROUTER"
const val KOIN_NAV_DIC_CICERONE = "KOIN_NAV_DIC_CICERONE"

const val KOIN_NAV_VOC_ROUTER  = "KOIN_NAV_VOC_ROUTER"
const val KOIN_NAV_VOC_CICERONE  = "KOIN_NAV_VOC_CICERONE"



val navigationModule = module {

    single<Cicerone<SwitchRouter>>(named(KOIN_NAV_MAIN_CICERONE)) {
        Cicerone.create(SwitchRouter())
    }

    single<SwitchRouter>(named(KOIN_NAV_MAIN_ROUTER)) {
        get<Cicerone<SwitchRouter>>(named(KOIN_NAV_MAIN_CICERONE)).router
    }

    single<Cicerone<Router>>(named(KOIN_NAV_DIC_CICERONE)){
        Cicerone.create()
    }

    single<Router>(named(KOIN_NAV_DIC_ROUTER)){
        get<Cicerone<Router>>(named(KOIN_NAV_DIC_CICERONE)).router
    }

    single<Cicerone<Router>>(named(KOIN_NAV_VOC_CICERONE)){
        Cicerone.create()
    }

    single<Router>(named(KOIN_NAV_VOC_ROUTER)){
        get<Cicerone<Router>>(named(KOIN_NAV_VOC_CICERONE)).router
    }
}
