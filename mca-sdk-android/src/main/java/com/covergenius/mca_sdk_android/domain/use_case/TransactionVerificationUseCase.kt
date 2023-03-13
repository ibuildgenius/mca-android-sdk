package com.covergenius.mca_sdk_android.domain.use_case

import android.util.Log
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.domain.model.TransactionUpdate
import com.covergenius.mca_sdk_android.domain.repo.InitiatePurchaseRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TransactionVerificationUseCase @Inject constructor(val repo: InitiatePurchaseRepo) {
    private val SCOPE = "VERIFY_TRANSAC_USE_CASE"

    operator fun invoke(token: String, payload: String): Flow<Resource<TransactionUpdate>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.verifyTransaction(token, payload)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            Log.e(SCOPE, e.message(), e)
            emit(Resource.Error(e.response()?.raw().toString() ?: "Opps an unexpected error occurred"))
        } catch (e: IOException) {
            Log.e(SCOPE, "", e)
            emit(Resource.Error("Could not reach server. Check your internet connection"))
        }
    }
}