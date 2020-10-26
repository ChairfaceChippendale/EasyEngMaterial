package com.ujujzk.ee.catalog

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.ujujzk.ee.domain.usecase.dic.ObserveDictionariesUseCase
import com.ujujzk.ee.domain.usecase.dic.TestArticleDefinitionUseCase
import com.ujujzk.ee.domain.usecase.voc.GetPackUseCase
import com.ujujzk.ee.presentation.base.BaseViewModel
import com.ujujzk.ee.presentation.model.VDictionary

class CatalogViewModel(
    private val logger: (String) -> Unit,
    private val router: Router,
    observeDictionariesUc: ObserveDictionariesUseCase,
    test: TestArticleDefinitionUseCase,
    getPackUseCase: GetPackUseCase
): BaseViewModel(logger) {

    val dictionaries = MutableLiveData<List<VDictionary>>()


    init {


        //TODO TEMP
        observeDictionariesUc.launch(ObserveDictionariesUseCase.Params.get()){
            onNext = {
                dictionaries.value = it.map { VDictionary(it.id, it.dictionaryName) }
            }
            onError = { handleCommonErrors(it) }
        }


        //TODO TEMP
        test.launch(
            TestArticleDefinitionUseCase.Params.get()){
            onComplete = {
            }
            onError = { handleCommonErrors(it) }
        }

        //TODO TEMP TEST LOGGING interceptor
//        getPackUseCase.executeBy(
//            GetPackUseCase.Params.get(),
//            onComplete = {
//                logger("Voc test Complete")
//            },
//            onError = {
//                it.printStackTrace()
//            }
//        )
    }

    override fun onBackPressed() : Boolean {
        router.exit()
        return true
    }

}