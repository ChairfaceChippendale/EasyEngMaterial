package com.ujujzk.ee.data.tools.mapper

interface DataMapper<in SOURCE, out RESULT> {

    fun convert(from: SOURCE, mapper:MapperDelegate): RESULT

}