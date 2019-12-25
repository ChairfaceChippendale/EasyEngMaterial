package com.ujujzk.ee.ui.dictionary.catalog

import android.text.Html
import androidx.lifecycle.MutableLiveData
import com.ujujzk.ee.domain.usecase.dic.ObserveDictionariesUseCase
import com.ujujzk.ee.domain.usecase.dic.TestArticleDefinitionUseCase
import com.ujujzk.ee.domain.usecase.voc.GetPackUseCase
import com.ujujzk.ee.ui.base.BaseViewModel
import com.ujujzk.ee.ui.model.VDictionary
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

class CatalogViewModel(
    disposables: CompositeDisposable,
    private val logger: (String) -> Unit,
    private val router: Router,
    observeDictionariesUseCase: ObserveDictionariesUseCase,
    test: TestArticleDefinitionUseCase,
    getPackUseCase: GetPackUseCase
): BaseViewModel(disposables) {

    val arttext: MutableLiveData<CharSequence> by lazy { MutableLiveData<CharSequence>() }

    val title: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val dicListAdapter: MutableLiveData<DicAdapter> by lazy { MutableLiveData<DicAdapter>() }

    init {

        title.value = "Dictionaries"

        //TODO TEMP
        observeDictionariesUseCase.executeBy(
            ObserveDictionariesUseCase.Params.get(),
            onNext = {dictionaries ->

                dicListAdapter.value =
                    DicAdapter().also { it.data = dictionaries.map { VDictionary(it.id, it.dictionaryName) } }

            },
            onError = {
                it.printStackTrace()
            }
        )

        //TODO TEMP
        test.executeBy(
            TestArticleDefinitionUseCase.Params.get(),
            onSuccess = {
                arttext.value = Html.fromHtml(it.wordDefinition)
            },
            onError = {
                it.printStackTrace()
            }
        )

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

    fun onBackPressed() : Boolean {
        router.exit()
        return true
    }

}