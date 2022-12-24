package com.covergenius.mca_sdk_android.domain.repo

import com.covergenius.mca_sdk_android.data.remote.dto.Response
import com.covergenius.mca_sdk_android.domain.model.PaymentOption

interface InitRepo {
    suspend fun initialise(token: String, paymentOption: PaymentOption): Response
}