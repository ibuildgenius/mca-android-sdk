package com.covergenius.mca_sdk_android.domain.use_case

import android.util.Log
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import com.covergenius.mca_sdk_android.domain.repo.InitiatePurchaseRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InitiatePurchaseUseCase @Inject constructor(val repo: InitiatePurchaseRepo) {
    private val SCOPE = "INIT_PURCHASE_USE_CASE"

    operator fun invoke(token: String, payload: String): Flow<Resource<PaymentResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.initiatePurchase(token, payload)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            Log.e(SCOPE, e.message(), e)
            emit(Resource.Error("Opps an unexpected error occurred"))
        } catch (e: IOException) {
            Log.e(SCOPE, "", e)
            emit(Resource.Error("Could not reach server. Check your internet connection"))
        }
    }
}