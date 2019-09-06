package com.ujujzk.ee.ui.navigation

import ru.terrakok.cicerone.BaseRouter

class SwitchRouter : BaseRouter() {
    fun switchFragment(fr: FlowFragment) {
        executeCommands(SwitchNavigator.SwitchFragment(fr))
    }
}