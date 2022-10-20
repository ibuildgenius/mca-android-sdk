package com.covergenius.mca_sdk_android.data.remote

import com.covergenius.mca_sdk_android.data.remote.dto.Response
import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface API {
    @Headers("Content-Type: application/json")
    @POST("/v1/sdk/initialize")
    suspend fun initialize(
        @Header("Authorization") token: String,
        @Body body: String): Response


    @Headers("Content-Type: application/json")
    @POST("/v1/sdk/initiate-purchase")
    suspend fun initiatePurchase(
        @Header("Authorization") token: String,
        @Body payload: String): PaymentResponse


    @Headers("Content-Type: application/json")
    @POST("/v1/sdk/complete-purchase")
    suspend fun completePurchase(@Header("Authorization")token: String, @Body payload: String)
}