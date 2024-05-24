package com.sopt.now.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig.AUTH_BASE_URL
import com.sopt.now.BuildConfig.USER_BASE_URL
import com.sopt.now.data.api.AuthServiceApi
import com.sopt.now.data.api.UserServiceApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiFactory {
    private fun getLogOkHttpClient(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("Retrofit2", "CONNECTION INFO -> $message")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(getLogOkHttpClient())
        .build()

    fun retrofit(url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

    inline fun <reified T, B> create(url: B): T = retrofit(url.toString()).create(T::class.java)
}

object ServicePool {
    val authServiceApi = ApiFactory.create<AuthServiceApi, String>(AUTH_BASE_URL)
    val userServiceApi = ApiFactory.create<UserServiceApi, String>(USER_BASE_URL)
}