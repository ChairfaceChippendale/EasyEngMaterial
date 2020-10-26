package com.ujujzk.ee.presentation.navigation.switchnav

import com.github.terrakok.cicerone.BaseRouter
import com.ujujzk.ee.presentation.navigation.FlowFragment

class SwitchRouter : BaseRouter() {
    fun switchFragment(fr: FlowFragment) {
        executeCommands(SwitchNavigator.SwitchFragment(fr))
    }
}