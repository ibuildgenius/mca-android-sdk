package com.covergenius.mca_sdk_android.data.remote.dto.payment

import com.google.gson.annotations.SerializedName

class Data(
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("bank")
    val bank: String,
    @SerializedName("reference")
    val reference: String
)