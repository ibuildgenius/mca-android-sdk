package com.covergenius.mca_sdk_android.domain.use_case

import android.util.Log
import com.covergenius.mca_sdk_android.Credentials
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.data.remote.API
import com.covergenius.mca_sdk_android.domain.model.Credential
import com.covergenius.mca_sdk_android.domain.model.SelectModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SelectFieldsUseCase @Inject constructor(val api: API) {
val SCOPE = "SELECT_USE_CASE"
    operator fun invoke(url: String): Flow<Resource<SelectModel>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getData(url, "Bearer ${Credentials.token}")
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            Log.e(SCOPE, e.message(), e)
            emit(Resource.Error("Ops an unexpected error occurred"))
        } catch (e: IOException) {
            Log.e(SCOPE, "", e)
            emit(Resource.Error("Could not reach server"))
        }
    }
}