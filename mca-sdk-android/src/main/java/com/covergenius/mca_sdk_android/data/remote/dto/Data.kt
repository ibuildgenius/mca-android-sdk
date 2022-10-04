package com.covergenius.mca_sdk_android.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("businessDetails")
    val businessDetails: BusinessDetails,
    @SerializedName("productDetails")
    val productDetails: List<ProductDetail>
)