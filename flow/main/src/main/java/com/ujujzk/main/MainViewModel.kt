package com.ujujzk.main

import com.github.terrakok.cicerone.Router
import com.ujujzk.ee.presentation.base.BaseViewModel
import com.ujujzk.ee.presentation.navigation.FlowFragment
import com.ujujzk.ee.presentation.navigation.switchnav.SwitchRouter


class MainViewModel(
    private val logger: (String) -> Unit,
    private val appRouter: Router,
    private val mainRouter: SwitchRouter
) : BaseViewModel(logger) {

    override fun onBackPressed(): Boolean {
        appRouter.exit()
        return true
    }

    fun onDictionaryClick(searchParent: FlowFragment) {
        mainRouter.switchFragment(searchParent)
    }

    fun onVocabularyClick(vocabularyParent: FlowFragment) {
        mainRouter.switchFragment(vocabularyParent)
    }

//    fun onGrammarClick(grammarParent: FlowFragment) {
//        mainRouter.switchFragment(grammarParent)
//    }
}