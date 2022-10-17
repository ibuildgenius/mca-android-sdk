package com.covergenius.mca_sdk_android.domain.model

import com.google.gson.annotations.SerializedName

enum class PaymentChannel {
    USSD,
    Transfer
}

data class PaymentModel (
    val channel: PaymentChannel,
    @SerializedName("bank-code")
    val bankCode: String?,
    val amount: String?
)