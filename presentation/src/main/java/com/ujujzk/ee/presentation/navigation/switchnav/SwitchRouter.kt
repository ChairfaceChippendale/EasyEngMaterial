package com.ujujzk.ee.presentation.navigation.switchnav

import com.ujujzk.ee.presentation.navigation.FlowFragment
import ru.terrakok.cicerone.BaseRouter

class SwitchRouter : BaseRouter() {
    fun switchFragment(fr: FlowFragment) {
        executeCommands(SwitchNavigator.SwitchFragment(fr))
    }
}