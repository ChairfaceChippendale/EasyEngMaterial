package com.ujujzk.ee.pack

import com.github.terrakok.cicerone.Router
import com.ujujzk.ee.presentation.base.BaseViewModel


class PackViewModel(
    private val logger: (String) -> Unit,
    private val router: Router
): BaseViewModel(logger) {

    override fun onBackPressed() : Boolean {
        router.exit()
        return true
    }
}