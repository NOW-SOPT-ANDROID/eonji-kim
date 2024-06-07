package com.sopt.now.compose.di

import com.sopt.now.compose.data.datasource.ReqresDataSource
import com.sopt.now.compose.data.repositoryimpl.ReqresRepositoryImpl
import com.sopt.now.compose.data_remote.datasourceimpl.ReqresDataSourceImpl
import com.sopt.now.compose.domain.repository.ReqresRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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