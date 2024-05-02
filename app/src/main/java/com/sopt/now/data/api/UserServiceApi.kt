package com.sopt.now.data.api

import com.sopt.now.data.KeyStorage.API
import com.sopt.now.data.KeyStorage.USERS
import com.sopt.now.data.dto.response.ResponseUserListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserServiceApi {
    @GET("$API/$USERS")
    suspend fun getUserList(
        @Query("page") page: Int
    ): Response<ResponseUserListDto>
}