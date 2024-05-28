package com.sopt.now.di

import com.sopt.now.data.datasource.ReqresDataSource
import com.sopt.now.data.repositoryimpl.ReqresRepositoryImpl
import com.sopt.now.data_remote.api.ReqresApiService
import com.sopt.now.data_remote.datasourceimpl.ReqresDataSourceImpl
import com.sopt.now.domain.repository.ReqresRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReqresModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsReqresRepository(RepositoryImpl: ReqresRepositoryImpl): ReqresRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun providesReqresDataSource(DataSourceImpl: ReqresDataSourceImpl): ReqresDataSource
    }
}