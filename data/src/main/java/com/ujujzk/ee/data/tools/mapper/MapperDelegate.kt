package com.ujujzk.ee.data.tools.mapper

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MapperDelegate private constructor(val converters: Map<ConverterKey, ConverterHolder>) {

    @Suppress("UNCHECKED_CAST")
    inline fun <reified FROM, reified TO> convert(from: FROM): TO {

        val sourceTypeToken = object : TypeRef<FROM>(){}.getToken()
        val resultTypeToken = object : TypeRef<TO>(){}.getToken()

        val converter = converters[ConverterKey(sourceTypeToken, resultTypeToken)]

        return if (
            converter?.sourceClass == sourceTypeToken
            && converter.resultClass == resultTypeToken
        ) {
            (converter.converter as DataMapper<FROM, TO>).convert(from, this)
        } else {
            throw IllegalArgumentException("Converter with source $sourceTypeToken and target $resultTypeToken not registered")
        }
    }

    data class ConverterKey(val clazzFrom: TypeToken, val clazzTo: TypeToken)

    class ConverterHolder(
        val sourceClass: TypeToken,
        val resultClass: TypeToken,
        val converter: DataMapper<*, *>
    )

    class Builder {

        private val converters: MutableMap<ConverterKey, ConverterHolder> = mutableMapOf()

        fun <SOURCE, RESULT> registerConverter(
            sourceClass: TypeRef<SOURCE>,
            resultClass: TypeRef<RESULT>,
            converter: DataMapper<SOURCE, RESULT>
        ): Builder {
            converters[ConverterKey(clazzFrom = sourceClass.getToken(), clazzTo = resultClass.getToken())] =
                ConverterHolder(
                    sourceClass = sourceClass.getToken(),
                    resultClass = resultClass.getToken(),
                    converter = converter
                )

            return this
        }

        fun build(): MapperDelegate {
            return MapperDelegate(converters)
        }
    }

    abstract class TypeRef<T>  {
        private val type: Type

        init {
            val superclass = javaClass.genericSuperclass as ParameterizedType
            type = superclass.actualTypeArguments[0]
        }

        fun getToken(): TypeToken = TypeToken(type.toString())

    }

    data class TypeToken(val token: String)

}