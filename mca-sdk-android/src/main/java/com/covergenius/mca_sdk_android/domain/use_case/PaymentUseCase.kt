package com.covergenius.mca_sdk_android.domain.use_case

import android.util.Log
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.data.remote.dto.payment.PaymentResponse
import com.covergenius.mca_sdk_android.domain.repo.PaymentRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PaymentUseCase @Inject constructor(val repo: PaymentRepo) {

    private val SCOPE = "PAYMENT_USE_CASE"
    operator fun invoke(token: String, payload: String): Flow<Resource<PaymentResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.buyProduct(token, payload)
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