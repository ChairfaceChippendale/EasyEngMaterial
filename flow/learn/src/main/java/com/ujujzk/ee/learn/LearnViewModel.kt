package com.ujujzk.ee.learn

import com.ujujzk.ee.presentation.base.BaseViewModel
import ru.terrakok.cicerone.Router

class LearnViewModel(
    private val logger: (String) -> Unit,
    private val router: Router
): BaseViewModel(logger) {

    override fun onBackPressed() : Boolean {
        router.exit()
        return true
    }
}