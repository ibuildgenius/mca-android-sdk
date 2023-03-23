package com.covergenius.mca_sdk_android.presentation.views.payment

import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse

data class ViewState<T>(
    val isLoading: Boolean = false,
    val response: T? = null,
    val error: String = ""
)