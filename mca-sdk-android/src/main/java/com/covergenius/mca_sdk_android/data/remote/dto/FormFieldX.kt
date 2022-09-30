package com.covergenius.mca_sdk_android.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FormFieldX(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)