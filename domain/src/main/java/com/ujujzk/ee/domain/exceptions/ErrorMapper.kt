package com.ujujzk.ee.domain.exceptions


interface ErrorMapper{
    fun mapError(error: Throwable): Throwable
}