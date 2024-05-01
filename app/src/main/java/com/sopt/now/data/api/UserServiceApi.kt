package com.sopt.now.data.api

import com.sopt.now.data.KeyStorage.INFO
import com.sopt.now.data.KeyStorage.MEMBER
import com.sopt.now.data.dto.response.ResponseGetUserInfoDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserServiceApi {
    @GET("$MEMBER/$INFO")
    suspend fun getUserInfo(
        @Header("memberId") memberId: Int
    ): ResponseGetUserInfoDto
}