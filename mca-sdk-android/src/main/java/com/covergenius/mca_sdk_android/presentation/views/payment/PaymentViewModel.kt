package com.covergenius.mca_sdk_android.presentation.views.payment

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.covergenius.mca_sdk_android.data.cache.BUSINESS_INSTANCE_ID
import com.covergenius.mca_sdk_android.data.cache.SAVED_FORM_DATA_ENTRY
import com.covergenius.mca_sdk_android.data.cache.getSelectedProduct
import com.covergenius.mca_sdk_android.data.cache.getString
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.domain.model.PaymentChannel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    private val context =
        getApplication<Application>().applicationContext //TODO("implement a better solution")

    var selectedPaymentMethod = mutableStateOf(PaymentChannel.Transfer)

    val product = mutableStateOf<ProductDetail?>(null)
    val formData = mutableStateOf("")
    val businessDetails = mutableStateOf("")

    init {
        product.value = context.getSelectedProduct()
        formData.value = context.getString(SAVED_FORM_DATA_ENTRY)
        businessDetails.value = context.getString(BUSINESS_INSTANCE_ID)
    }

    fun getField(key: String): String? {
        val entry = Gson().fromJson(formData.value, JsonObject::class.java)
        return entry.get(key).asString
    }
}