package com.covergenius.mca_sdk_android.data.repo

import com.covergenius.mca_sdk_android.data.remote.API
import com.covergenius.mca_sdk_android.data.remote.dto.Response
import com.covergenius.mca_sdk_android.domain.model.PaymentOption
import com.covergenius.mca_sdk_android.domain.repo.InitRepo
import org.json.JSONObject
import javax.inject.Inject

class InitRepoImpl @Inject constructor(private val api: API) : InitRepo {
    override suspend fun initialise(token: String, paymentOption: PaymentOption): Response {
        val body = JSONObject().put ("payment_option", paymentOption.name.lowercase()).toString()
        return api.initialize("Bearer $token", body)
    }
}