package com.covergenius.mca_sdk_android.domain.model

import com.google.gson.annotations.SerializedName

enum class PaymentChannel {
    USSD,
    Transfer
}


fun resolvedPaymentChannel(p: PaymentChannel): String {
    return when (p) {
        PaymentChannel.Transfer -> "bank transfer"
        PaymentChannel.USSD -> "ussd"
    }
}


data class PaymentModel(
    val channel: PaymentChannel,
    @SerializedName("bank-code")
    val bankCode: String?,
    val amount: String?
)