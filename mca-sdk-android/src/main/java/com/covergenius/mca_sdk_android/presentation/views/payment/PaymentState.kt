package com.covergenius.mca_sdk_android.presentation.views.payment

import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse

data class PaymentState(
    val isLoading: Boolean = false,
    val paymentResponse: PaymentResponse? = null,
    val error: String = ""
)