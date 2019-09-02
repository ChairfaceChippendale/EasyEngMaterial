package com.ujujzk.ee.ui.di

import com.ujujzk.ee.ui.navigation.SwitchRouter
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val navigationModule = module {

    single<SwitchRouter>(named("nav_main")) {
        get<Cicerone<SwitchRouter>>(named("nav_main")).router
    }

    single<Cicerone<SwitchRouter>>(named("nav_main")) {
        Cicerone.create(SwitchRouter())
    }

    single<Router>(named("nav_dic")){
        get<Cicerone<Router>>(named("nav_dic")).router
    }

    single<Cicerone<Router>>(named("nav_dic")){
        Cicerone.create()
    }

    single<Router>(named("nav_voc")){
        get<Cicerone<Router>>(named("nav_voc")).router
    }

    single<Cicerone<Router>>(named("nav_voc")){
        Cicerone.create()
    }

}
