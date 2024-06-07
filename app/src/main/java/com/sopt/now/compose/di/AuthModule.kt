package com.sopt.now.compose.di

import com.sopt.now.compose.data.datasource.AuthDataSource
import com.sopt.now.compose.data.repositoryimpl.AuthRepositoryImpl
import com.sopt.now.compose.data_remote.datasourceimpl.AuthDataSourceImpl
import com.sopt.now.compose.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsAuthRepository(RepositoryImpl: AuthRepositoryImpl): AuthRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun providesAuthDataSource(DataSourceImpl: AuthDataSourceImpl): AuthDataSource
    }
}