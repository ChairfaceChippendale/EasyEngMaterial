package com.ujujzk.ee.data.source.voc

import com.ujujzk.ee.domain.gateway.VocGateway
import io.reactivex.Completable

class VocGatewayImpl(
    val remote: VocRemote
): VocGateway {

    override fun testVoc(): Completable {
        return remote.testVoc()
            .map {
                it
            }
            .ignoreElement()
    }
}