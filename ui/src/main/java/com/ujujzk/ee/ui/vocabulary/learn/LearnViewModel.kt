package com.ujujzk.ee.ui.vocabulary.learn

import com.ujujzk.ee.ui.base.BaseViewModel
import com.ujujzk.ee.ui.vocabulary.learn.LearnFragment
import com.ujujzk.ee.ui.vocabulary.pack.PackFragment
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

class LearnViewModel(
    disposables: CompositeDisposable,
    private val logger: (String) -> Unit,
    private val router: Router
): BaseViewModel(disposables) {

    fun onBackPressed() : Boolean {
        router.exit()
        return true
    }
}