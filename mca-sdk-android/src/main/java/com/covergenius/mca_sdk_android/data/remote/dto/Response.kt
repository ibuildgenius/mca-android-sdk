package com.covergenius.mca_sdk_android.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("responseCode")
    val responseCode: Int,
    @SerializedName("responseText")
    val responseText: String
)