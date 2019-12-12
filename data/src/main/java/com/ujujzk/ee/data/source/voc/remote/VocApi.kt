package com.ujujzk.ee.data.source.voc.remote

import com.ujujzk.ee.data.source.voc.remote.model.VocResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface VocApi {

    @GET("voc")
    fun textVoc(
        @Header("Refresh-Token") refreshToken: String
    ): Single<VocResponse>

}