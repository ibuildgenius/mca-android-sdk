package com.covergenius.mca_sdk_android.data.repo

import com.covergenius.mca_sdk_android.data.remote.API
import com.covergenius.mca_sdk_android.data.remote.dto.Response
import com.covergenius.mca_sdk_android.domain.model.PaymentOption
import com.covergenius.mca_sdk_android.domain.repo.InitRepo
import javax.inject.Inject

class InitRepoImpl @Inject constructor(private val api: API) : InitRepo {
    override suspend fun initialise(token: String, paymentOption: PaymentOption): Response =
        api.initialize("Bearer $token","{payment_option: ${paymentOption.name}}" )
}