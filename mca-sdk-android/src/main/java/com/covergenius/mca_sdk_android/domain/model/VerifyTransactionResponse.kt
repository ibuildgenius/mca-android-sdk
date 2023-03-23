package com.covergenius.mca_sdk_android.domain.model

data class VerifyTransactionResponse(
    val data: Data,
    val responseCode: Int,
    val responseText: String
)