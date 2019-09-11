package com.ujujzk.ee.data.source.dic

import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable

interface DicStorage {

    fun observeDictionaries(): Flowable<List<Dictionary>>

}