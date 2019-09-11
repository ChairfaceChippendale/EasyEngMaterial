package com.ujujzk.ee.data.source.dic.local.model

import com.ujujzk.ee.data.tools.mapper.DataMapper
import com.ujujzk.ee.data.tools.mapper.MapperDelegate
import com.ujujzk.ee.domain.usecase.dic.model.Dictionary

object DictionaryFromRoomToDomain : DataMapper<DictionaryRoom, Dictionary> {
    override fun convert(from: DictionaryRoom, mapper: MapperDelegate): Dictionary {
        return from.run {
            Dictionary(
                id = id,
                dictionaryName = name
            )
        }
    }
}

object DictionariesFromRoomToDomain : DataMapper<List<DictionaryRoom>, List<Dictionary>> {
    override fun convert(from: List<DictionaryRoom>, mapper: MapperDelegate): List<Dictionary> {
        return from.map { mapper.convert<DictionaryRoom, Dictionary>(it) }
    }
}