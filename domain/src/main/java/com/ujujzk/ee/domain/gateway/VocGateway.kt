package com.ujujzk.ee.domain.gateway

import io.reactivex.Completable

interface VocGateway {

    fun testVoc(): Completable
}