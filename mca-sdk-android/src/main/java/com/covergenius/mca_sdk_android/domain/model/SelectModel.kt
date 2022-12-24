package com.covergenius.mca_sdk_android.domain.model


import com.google.gson.annotations.SerializedName

class SelectModel(
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("responseCode")
    val responseCode: Int,
    @SerializedName("responseText")
    val responseText: String
)