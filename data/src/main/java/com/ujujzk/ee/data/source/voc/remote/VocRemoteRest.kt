package com.ujujzk.ee.data.source.voc.remote

import com.ujujzk.ee.data.source.voc.VocRemote
import com.ujujzk.ee.data.source.voc.remote.model.VocResponse
import io.reactivex.Single

class VocRemoteRest(
    private val api: VocApi
): VocRemote {

    override fun testVoc(): Single<VocResponse>{
        return api.textVoc("Heloo")
    }
}