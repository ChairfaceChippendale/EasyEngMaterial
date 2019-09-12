package com.ujujzk.ee.data.source.dic.local


import com.ujujzk.ee.data.source.dic.DicStorage
import com.ujujzk.ee.data.source.dic.local.model.DictionaryRoom
import com.ujujzk.ee.data.tools.mapper.MapperDelegate
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable


class DicStorageRoom(
    private val dicDao: DictionaryDao,
    private val mapper: MapperDelegate
) : DicStorage {

    override fun observeDictionaries(): Flowable<List<Dictionary>> {

        return Flowable.just(listOf(DictionaryRoom("id1", "Dic1"), DictionaryRoom("id2", "Dic2"), DictionaryRoom("id3", "Dic3"))).map { mapper.convert<List<DictionaryRoom>, List<Dictionary>>(it)  }

//        return dicDao.observeAll().map { mapper.convert<List<DictionaryRoom>, List<Dictionary>>(it) }
    }

}