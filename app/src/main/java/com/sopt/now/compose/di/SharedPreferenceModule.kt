package com.sopt.now.compose.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sopt.now.compose.data.datasource.SharedPreferenceDataSource
import com.sopt.now.compose.data_local.datasourceimpl.SharedPreferencesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {
    @Provides
    @Singleton
    fun providesLocalPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            context.packageName,
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun providesSharedPreferenceDataSource(DataSourceImpl: SharedPreferencesDataSourceImpl): SharedPreferenceDataSource
    }
}