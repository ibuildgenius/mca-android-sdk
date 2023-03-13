package com.covergenius.mca_sdk_android.domain.repo

import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import com.covergenius.mca_sdk_android.domain.model.TransactionUpdate

interface InitiatePurchaseRepo {
     suspend fun initiatePurchase(token: String, payload: String): PaymentResponse
     suspend fun completePurchase(token: String, payload: String): Any
     suspend fun verifyTransaction(token: String, payload: String): TransactionUpdate
}