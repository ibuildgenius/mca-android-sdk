package com.covergenius.mca_sdk_android.data.repo

import com.covergenius.mca_sdk_android.data.remote.API
import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import com.covergenius.mca_sdk_android.domain.repo.PaymentRepo
import javax.inject.Inject

class PaymentRepoImpl @Inject constructor(private val api: API) : PaymentRepo {
    override suspend fun buyProduct(token: String, payload: String): PaymentResponse {
        return api.buyProduct("Bearer $token", payload)
    }
}