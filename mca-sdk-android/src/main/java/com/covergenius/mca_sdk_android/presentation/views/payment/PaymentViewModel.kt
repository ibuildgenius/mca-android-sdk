package com.covergenius.mca_sdk_android.presentation.views.payment

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.covergenius.mca_sdk_android.API_KEY
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.data.cache.*
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.domain.model.PaymentChannel
import com.covergenius.mca_sdk_android.domain.model.resolvedPaymentChannel
import com.covergenius.mca_sdk_android.domain.use_case.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel @Inject constructor(
    application: Application,
    private val paymentUseCase: PaymentUseCase
) :
    AndroidViewModel(application) {
    private val context =
        getApplication<Application>().applicationContext //TODO("implement a better solution")

    var selectedPaymentMethod = mutableStateOf(PaymentChannel.Transfer)

    val product = mutableStateOf<ProductDetail?>(null)
    val formData = mutableStateOf("")
    val businessDetails = mutableStateOf("")

     val _state = mutableStateOf(PaymentState())


    init {
        product.value = context.getSelectedProduct()
        formData.value = context.getString(SAVED_FORM_DATA_ENTRY)
        businessDetails.value = context.getString(BUSINESS_INSTANCE_ID)
    }

    fun initializePurchase() {
        val jsonObject = JSONObject()

        jsonObject.put("payload", JSONObject(formData.value))
        jsonObject.put("instance_id", "${getFieldFromJson("instance_id", businessDetails.value)}")
        jsonObject.put("payment_channel",JSONObject("{\"channel\": \"${resolvedPaymentChannel(selectedPaymentMethod.value)}\"}"))


        Log.i("", "Payload is $jsonObject")

        paymentUseCase(API_KEY, jsonObject.toString()).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = PaymentState(error = result.message ?: "A server error occurred")
                }
                is Resource.Loading -> {
                    _state.value = PaymentState(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = PaymentState(paymentResponse = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}