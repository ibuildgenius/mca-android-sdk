package com.covergenius.mca_sdk_android.di

import com.covergenius.mca_sdk_android.common.Constants
import com.covergenius.mca_sdk_android.data.remote.API
import com.covergenius.mca_sdk_android.data.repo.InitRepoImpl
import com.covergenius.mca_sdk_android.domain.repo.InitRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SdkModule {

    @Provides
    @Singleton
    fun provideAPI(): API {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideRepo(api: API): InitRepo {
        return InitRepoImpl(api)
    }
}