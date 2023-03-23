package com.covergenius.mca_sdk_android.data.repo

import android.content.ContentResolver
import android.net.Uri
import com.covergenius.mca_sdk_android.data.helper.ContentUriRequestBody
import com.covergenius.mca_sdk_android.data.remote.API
import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import com.covergenius.mca_sdk_android.domain.model.VerifyTransactionResponse
import com.covergenius.mca_sdk_android.domain.repo.InitiatePurchaseRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class InitiatePurchaseRepoImpl @Inject constructor(private val api: API) : InitiatePurchaseRepo {
    override suspend fun initiatePurchase(token: String, payload: String): PaymentResponse {
        return api.initiatePurchase("Bearer $token", payload)
    }

    override suspend fun completePurchase(token: String, payload: String): Any {
        return api.completePurchase("Bearer $token", payload)
    }

    override suspend fun verifyTransaction(
        token: String,
        payload: String
    ): VerifyTransactionResponse {
        return api.verifyTransaction("Bearer $token", payload)
    }

    override suspend fun uploadFile(token: String, uri: Uri, contentResolver: ContentResolver): Map<String, Any> {
        //uri.file?.let {


        return api.fileUpload(
            MultipartBody.Part.createFormData("file", "image.png", ContentUriRequestBody(contentResolver = contentResolver, contentUri = uri)),
            MultipartBody.Part.createFormData("fileType", "image"),
            "Bearer $token"
        )
        //}
        //return mapOf()
    }


}