package com.covergenius.mca_sdk_android.data.remote.dto


import com.google.gson.annotations.SerializedName

data class BusinessDetails(
    @SerializedName("business_name")
    val businessName: String,
    @SerializedName("color_theme")
    val colorTheme: Any,
    @SerializedName("debit_wallet")
    val debitWallet: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("instance_id")
    val instanceId: String,
    @SerializedName("logo")
    val logo: Any,
    @SerializedName("payment_channels")
    val paymentChannels: List<String>,
    @SerializedName("trading_name")
    val tradingName: String
)