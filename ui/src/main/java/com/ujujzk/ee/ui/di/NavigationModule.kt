package com.ujujzk.ee.ui.di

import com.ujujzk.ee.ui.navigation.SwitchRouter
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

const val KOIN_NAV_MAIN = "nav_main"
const val KOIN_NAV_DIC = "nav_dic"
const val KOIN_NAV_VOC = "nav_voc"


val navigationModule = module {

    single<SwitchRouter>(named(KOIN_NAV_MAIN)) {
        get<Cicerone<SwitchRouter>>(named(KOIN_NAV_MAIN)).router
    }

    single<Cicerone<SwitchRouter>>(named(KOIN_NAV_MAIN)) {
        Cicerone.create(SwitchRouter())
    }

    single<Router>(named(KOIN_NAV_DIC)){
        get<Cicerone<Router>>(named(KOIN_NAV_DIC)).router
    }

    single<Cicerone<Router>>(named(KOIN_NAV_DIC)){
        Cicerone.create()
    }

    single<Router>(named(KOIN_NAV_VOC)){
        get<Cicerone<Router>>(named(KOIN_NAV_VOC)).router
    }

    single<Cicerone<Router>>(named(KOIN_NAV_VOC)){
        Cicerone.create()
    }

}
