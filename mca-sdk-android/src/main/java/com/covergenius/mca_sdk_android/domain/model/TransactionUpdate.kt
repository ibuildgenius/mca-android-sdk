package com.covergenius.mca_sdk_android.domain.model


import com.google.gson.annotations.SerializedName

class TransactionUpdate(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("narration")
    val narration: String,
    @SerializedName("status")
    val status: String
) {
    fun isSuccessful(): Boolean =
        this.status.lowercase() == "successful" || this.status.lowercase() == "success"

}