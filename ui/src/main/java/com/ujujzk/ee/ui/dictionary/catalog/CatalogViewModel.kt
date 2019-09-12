package com.ujujzk.ee.ui.dictionary.catalog

import com.ujujzk.ee.domain.usecase.dic.ObserveDictionariesUseCase
import com.ujujzk.ee.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

class CatalogViewModel(
    disposables: CompositeDisposable,
    private val logger: (String) -> Unit,
    private val router: Router,
    observeDictionariesUseCase: ObserveDictionariesUseCase
): BaseViewModel(disposables) {

    init {

        //TODO TEMP
        observeDictionariesUseCase.executeBy(
            ObserveDictionariesUseCase.Params.get(),
            onNext = {
                it.size
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun onBackPressed() : Boolean {
        router.exit()
        return true
    }


}