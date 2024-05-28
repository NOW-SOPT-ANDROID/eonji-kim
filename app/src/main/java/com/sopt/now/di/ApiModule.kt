package com.sopt.now.di

import com.sopt.now.data_remote.api.AuthApiService
import com.sopt.now.data_remote.api.ReqresApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideSignApiService(@AuthRetrofit retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideReqresApiService(@ReqresRetrofit retrofit: Retrofit): ReqresApiService =
        retrofit.create(ReqresApiService::class.java)
}