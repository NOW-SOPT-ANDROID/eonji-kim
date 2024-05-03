package com.sopt.now.compose.data.api

import com.sopt.now.compose.data.KeyStorage.API
import com.sopt.now.compose.data.KeyStorage.PAGE
import com.sopt.now.compose.data.KeyStorage.USERS
import com.sopt.now.compose.data.dto.response.ResponseUserListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserServiceApi {
    @GET("$API/$USERS")
    suspend fun getUserList(
        @Query(PAGE) page: Int
    ): Response<ResponseUserListDto>
}