package com.covergenius.mca_sdk_android.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FullBenefit(
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String
)