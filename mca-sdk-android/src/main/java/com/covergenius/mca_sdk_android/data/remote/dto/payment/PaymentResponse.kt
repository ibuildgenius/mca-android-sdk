package com.covergenius.mca_sdk_android.data.remote.dto.payment


import com.google.gson.annotations.SerializedName

class PaymentResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("responseCode")
    val responseCode: Int,
    @SerializedName("responseText")
    val responseText: String
)