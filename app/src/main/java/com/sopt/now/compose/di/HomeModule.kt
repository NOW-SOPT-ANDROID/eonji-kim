package com.sopt.now.compose.di

import com.sopt.now.compose.data.datasource.HomeDataSource
import com.sopt.now.compose.data.repositoryimpl.HomeRepositoryImpl
import com.sopt.now.compose.data_remote.datasourceimpl.HomeDataSourceImpl
import com.sopt.now.compose.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsHomeRepository(RepositoryImpl: HomeRepositoryImpl): HomeRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun providesHomeDataSource(DataSourceImpl: HomeDataSourceImpl): HomeDataSource
    }
}