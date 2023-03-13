package com.covergenius.mca_sdk_android.presentation.views.payment

import android.app.Application
import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.covergenius.mca_sdk_android.MCA_API_KEY
import com.covergenius.mca_sdk_android.PUSHER_APP_KEY
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.data.cache.*
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.domain.model.PaymentChannel
import com.covergenius.mca_sdk_android.domain.model.TransactionUpdate
import com.covergenius.mca_sdk_android.domain.model.resolvedPaymentChannel
import com.covergenius.mca_sdk_android.domain.use_case.InitiatePurchaseUseCase
import com.covergenius.mca_sdk_android.domain.use_case.TransactionVerificationUseCase
import com.google.gson.Gson
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    application: Application,
    private val initiatePurchaseUseCase: InitiatePurchaseUseCase,
    private val verifyTransactionUseCase: TransactionVerificationUseCase
) :
    AndroidViewModel(application) {

    var transactionStatus: MutableState<TransactionUpdate?> = mutableStateOf(null)

    private val context =
        getApplication<Application>().applicationContext //TODO("implement a better solution")

    var selectedPaymentMethod = mutableStateOf(PaymentChannel.Transfer)

    var waitCompleted =
        mutableStateOf(true)

    val product = mutableStateOf<ProductDetail?>(null)
    val formData = mutableStateOf("")
    val businessDetails = mutableStateOf("")

    var transSuccess = mutableStateOf(false)
    var transLoading = mutableStateOf(false)

    val _state = mutableStateOf(PaymentState())

    val showDialog = mutableStateOf(false)

    init {
        product.value = context.getSelectedProduct()
        formData.value = context.getString(SAVED_FORM_DATA_ENTRY)
        businessDetails.value = context.getString(BUSINESS_INSTANCE_ID)
    }


    private val _showCancelDialog =  MutableStateFlow(false)

    val showCancelDialog: StateFlow<Boolean> = _showCancelDialog.asStateFlow()

    fun onOpenDialogClicked() {
        _showCancelDialog.value = true
    }

    fun onDialogConfirm() {
        _showCancelDialog.value = false
        //continue execution
    }

    fun onDialogDismiss() {
        _showCancelDialog.value = false
    }


    fun initializePurchase() {
        val jsonObject = JSONObject()

        jsonObject.put("payload", JSONObject(formData.value))
        jsonObject.put("instance_id", "${getFieldFromJson("instance_id", businessDetails.value)}")
        jsonObject.put(
            "payment_channel",
            JSONObject("{\"channel\": \"${resolvedPaymentChannel(selectedPaymentMethod.value)}\"}")
        )

        Log.i("", "Payload is $jsonObject")

        initiatePurchaseUseCase(MCA_API_KEY, jsonObject.toString()).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    showDialog.value = false
                    _state.value = PaymentState(error = result.message ?: "A server error occurred")
                }
                is Resource.Loading -> {
                    showDialog.value = true
                    _state.value = PaymentState(isLoading = true)
                }

                is Resource.Success -> {
                    showDialog.value = false
                    waitCompleted.value = false
                    _state.value = PaymentState(paymentResponse = result.data)

                    _state.value.paymentResponse?.let {
                        listen(it.data.reference).launchIn(viewModelScope)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun verifyTransaction() {
        _state.value.paymentResponse?.let {
            val data = JSONObject()
            data.put("transaction_reference", it.data.reference)

            Log.i("", "Verifying with $data")

            verifyTransactionUseCase(MCA_API_KEY,data.toString()).onEach {
                result ->
                when(result) {
                    is Resource.Success -> {
                        transLoading.value = false
                        transSuccess.value = true

                        transactionStatus.value = result.data
                        waitCompleted.value = transactionStatus.value!!.isSuccessful()
                    }

                    is Resource.Error -> {
                        transLoading.value = false
                    }

                    is Resource.Loading -> {
                        transLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun listen(ref: String): Flow<Resource<TransactionUpdate>> = flow {
        val options = PusherOptions()
        options.setCluster("us2")

        val pusher = Pusher(PUSHER_APP_KEY, options)

        pusher.connect()

        val channel = pusher.subscribe("cache-$ref")

        channel.bind(
            "transaction_successful"
        ) { pusherEvent ->
            Log.i("PaymentViewModel", "Event data is ${pusherEvent.data}")
            val d = Gson().fromJson(pusherEvent.data, TransactionUpdate::class.java)
            waitCompleted.value = d.isSuccessful()
        }
    }

}