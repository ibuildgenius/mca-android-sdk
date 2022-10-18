package com.covergenius.mca_sdk_android.domain.repo

import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse

interface PaymentRepo {
     suspend fun buyProduct(token: String, payload: String): PaymentResponse
}