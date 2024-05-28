package com.sopt.now.di

import com.sopt.now.data.datasource.AuthDataSource
import com.sopt.now.data.repositoryimpl.AuthRepositoryImpl
import com.sopt.now.data_remote.datasourceimpl.AuthDataSourceImpl
import com.sopt.now.domain.repository.AuthRepository
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