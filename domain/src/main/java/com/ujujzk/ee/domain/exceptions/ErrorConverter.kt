package com.ujujzk.ee.domain.exceptions

class ErrorConverter(
    val errorMappers: Set<ErrorMapper>
) {

    //todo: make similar for other sources and cases
    fun convertError(error: Throwable): Throwable {

        errorMappers.onEach {
            val ex = it.mapError(error)
            if (ex is HandledException) return ex
        }

        return error
    }
}