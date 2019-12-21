package com.ujujzk.ee.data.source.dic.local

import com.ujujzk.ee.data.source.dic.DicStorage
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary
import io.reactivex.Flowable

class DicStorageFake: DicStorage {
    override fun observeDictionaries(): Flowable<List<Dictionary>> {

        return Flowable.just(listOf(
            Dictionary("d1", "Oxford Dictionary"),
            Dictionary("d2", "Universal Dictionary"),
            Dictionary("d3", "Urban Dictionary"),
            Dictionary("d4", "DSL Vok"),
            Dictionary("d5", "Ru-En"),
            Dictionary("d6", "Very Long name Dictionary Very long"),
            Dictionary("d7", "Oxford Dictionary"),
            Dictionary("d8", "Oxford Dictionary"),
            Dictionary("d9", "Oxford Dictionary"),
            Dictionary("d10", "Oxford Dictionary"),
            Dictionary("d11", "Oxford Dictionary"),
            Dictionary("d12", "Oxford Dictionary"),
            Dictionary("d13", "Oxford Dictionary"),
            Dictionary("d14", "Oxford Dictionary"),
            Dictionary("d15", "Oxford Dictionary"),
            Dictionary("d16", "Oxford Dictionary"),
            Dictionary("d17", "Oxford Dictionary"),
            Dictionary("d18", "Oxford Dictionary"),
            Dictionary("d19", "Oxford Dictionary"),
            Dictionary("d120", "Oxford Dictionary"),
            Dictionary("d163", "Oxford Dictionary")
        ))
    }
}