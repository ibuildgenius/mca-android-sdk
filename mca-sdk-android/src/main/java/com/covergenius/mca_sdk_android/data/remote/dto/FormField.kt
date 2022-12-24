package com.covergenius.mca_sdk_android.data.remote.dto

import android.os.Parcelable
import androidx.compose.ui.text.input.KeyboardType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormField(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("data_source")
    val dataSource: String,
    @SerializedName("data_type")
    val dataType: String,
    @SerializedName("data_url")
    val dataUrl: String,
    @SerializedName("depends_on")
    val dependsOn: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("error_msg")
    val errorMsg: String,
    @SerializedName("form_field")
    val formField: FormFieldX,
    @SerializedName("form_field_id")
    val formFieldId: String,
    @SerializedName("full_description")
    val fullDescription: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("input_type")
    val inputType: String,
    @SerializedName("is_live")
    val isLive: Boolean,
    @SerializedName("label")
    val label: String,
    @SerializedName("max")
    val max: Int,
    @SerializedName("min")
    val min: Int,
    @SerializedName("min_max_constraint")
    val minMaxConstraint: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("required")
    val required: Boolean,
    @SerializedName("show_first")
    val showFirst: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String
): Parcelable

fun List<FormField>.getPriorityFields(): List<FormField> =
    this.filter { it.showFirst }.sortedBy { it.position }


fun List<FormField>.getOtherFields(): List<FormField> =
    this.filter { !it.showFirst }.sortedBy { it.position }


fun FormField.getKeyboardType() : KeyboardType {
    return when(this.inputType.lowercase()) {
        "email" -> KeyboardType.Email
        "text" -> KeyboardType.Text
        "phone" -> KeyboardType.Phone
        else -> {KeyboardType.Text}
    }
}