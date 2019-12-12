package com.ujujzk.ee.data.source.voc

import com.ujujzk.ee.data.source.voc.remote.model.VocResponse
import io.reactivex.Single

interface VocRemote {

    fun testVoc(): Single<VocResponse>
}