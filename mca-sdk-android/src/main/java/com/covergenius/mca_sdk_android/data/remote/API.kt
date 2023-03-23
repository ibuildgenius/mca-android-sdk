package com.covergenius.mca_sdk_android.data.remote

import com.covergenius.mca_sdk_android.data.remote.dto.Response
import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import com.covergenius.mca_sdk_android.domain.model.SelectModel
import com.covergenius.mca_sdk_android.domain.model.TransactionUpdate
import com.covergenius.mca_sdk_android.domain.model.VerifyTransactionResponse
import okhttp3.MultipartBody
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

    @Headers("Content-Type: application/json")
    @POST("/v1/sdk/verify-transaction")
    suspend fun verifyTransaction(@Header("Authorization")token: String, @Body payload: String): VerifyTransactionResponse

    @GET
    suspend fun getData(@Url url: String, @Header("Authorization") token: String): SelectModel

    @Multipart
    @POST("/v1/upload-file")
    @Headers("Accept: application/json")
    suspend fun fileUpload(@Part body: MultipartBody.Part, @Part payload: MultipartBody.Part, @Header("Authorization") token: String): Map<String, Any>
}