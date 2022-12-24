package com.covergenius.mca_sdk_android.domain.use_case

import android.util.Log
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.domain.repo.InitiatePurchaseRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CompletePurchaseUseCase @Inject constructor(val repo: InitiatePurchaseRepo) {
    private val SCOPE = "COMPL_PURCHASE_USE_CASE"

    operator fun invoke(token: String, payload: String): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            val r = repo.completePurchase(token, payload)
            emit(Resource.Success(r))
        } catch (e: HttpException) {
            Log.e(SCOPE, e.message(), e)
            emit(Resource.Error("Opps an unexpected error occurred"))
        } catch (e: IOException) {
            Log.e(SCOPE, "", e)
            emit(Resource.Error("Could not reach server. Check your internet connection"))
        }
    }
}