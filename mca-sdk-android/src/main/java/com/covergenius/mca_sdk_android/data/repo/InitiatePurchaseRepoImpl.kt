package com.covergenius.mca_sdk_android.data.repo

import com.covergenius.mca_sdk_android.data.remote.API
import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import com.covergenius.mca_sdk_android.domain.repo.InitiatePurchaseRepo
import javax.inject.Inject

class InitiatePurchaseRepoImpl @Inject constructor(private val api: API) : InitiatePurchaseRepo {
    override suspend fun initiatePurchase(token: String, payload: String): PaymentResponse {
        return api.initiatePurchase("Bearer $token", payload)
    }

    override suspend fun completePurchase(token: String, payload: String): Any {
        return api.completePurchase("Bearer $token", payload)
    }
}