package com.covergenius.mca_sdk_android.data.remote

import com.covergenius.mca_sdk_android.data.remote.dto.Response
import com.covergenius.mca_sdk_android.domain.model.PaymentOption
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface API {
    @POST("v1/sdk/initialize")
    suspend fun initialize(@Header("Authorization") token: String, @Body paymentOption: String): Response
}