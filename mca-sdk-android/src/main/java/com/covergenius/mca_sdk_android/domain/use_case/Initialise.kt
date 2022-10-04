package com.covergenius.mca_sdk_android.domain.use_case

import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.data.remote.dto.Response
import com.covergenius.mca_sdk_android.domain.model.PaymentOption
import com.covergenius.mca_sdk_android.domain.repo.InitRepo
import com.covergenius.mca_sdk_android.common.utils.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject



const val SCOPE = "SDK_INITIALISATION_USE_CASE"
class InitialiseUseCase @Inject constructor(val repo: InitRepo) {

    operator fun invoke(token: String, paymentOption: PaymentOption): Flow<Resource<Response>> = flow {
      try {
          emit(Resource.Loading())

          val response = repo.initialise(token, paymentOption)

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