package com.covergenius.mca_sdk_android.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("plan_code")
    val planCode: String,
    @SerializedName("policy_type")
    val policyType: String,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("sectionType")
    val sectionType: String,
    @SerializedName("subClassId")
    val subClassId: String,
    @SerializedName("subClassName")
    val subClassName: String
)