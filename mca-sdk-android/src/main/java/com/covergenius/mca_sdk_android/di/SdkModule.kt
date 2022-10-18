package com.covergenius.mca_sdk_android.di

import com.covergenius.mca_sdk_android.common.Constants
import com.covergenius.mca_sdk_android.data.remote.API
import com.covergenius.mca_sdk_android.data.repo.InitRepoImpl
import com.covergenius.mca_sdk_android.data.repo.PaymentRepoImpl
import com.covergenius.mca_sdk_android.domain.repo.InitRepo
import com.covergenius.mca_sdk_android.domain.repo.PaymentRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SdkModule {

    @Provides
    @Singleton
    fun provideAPI(): API {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideInitRepo(api: API): InitRepo {
        return InitRepoImpl(api)
    }


    @Provides
    @Singleton
    fun providePaymentRepo(api: API): PaymentRepo {
        return PaymentRepoImpl(api)
    }
}