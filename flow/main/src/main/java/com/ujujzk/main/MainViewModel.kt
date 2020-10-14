package com.ujujzk.main

import com.ujujzk.ee.presentation.base.BaseViewModel
import com.ujujzk.ee.presentation.navigation.FlowFragment
import com.ujujzk.ee.presentation.navigation.switchnav.SwitchRouter
import ru.terrakok.cicerone.Router

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