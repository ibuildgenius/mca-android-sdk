package com.covergenius.mca_sdk_android.data.remote

import com.covergenius.mca_sdk_android.data.remote.dto.Response
import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import com.covergenius.mca_sdk_android.domain.model.SelectModel
import retrofit2.http.*

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


    @GET
    suspend fun getData(@Url url: String, @Header("Authorization") token: String): SelectModel
}