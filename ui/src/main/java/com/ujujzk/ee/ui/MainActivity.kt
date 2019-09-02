package com.ujujzk.ee.ui

import androidx.appcompat.app.AppCompatActivity
import com.ujujzk.ee.ui.navigation.SwitchNavigator
import com.ujujzk.ee.ui.navigation.SwitchRouter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Cicerone

class MainActivity : AppCompatActivity() {


    private val localCicerone : Cicerone<BaseRouter> by inject(named("main"))
    private val router: SwitchRouter by inject(named("main"))
    private val navigator = SwitchNavigator(this, supportFragmentManager, R.id.container)
}