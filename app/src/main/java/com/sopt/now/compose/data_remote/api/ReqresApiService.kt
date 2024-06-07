package com.sopt.now.compose.data_remote.api

import com.sopt.now.compose.data_remote.ApiKeyStorage.API
import com.sopt.now.compose.data_remote.ApiKeyStorage.PAGE
import com.sopt.now.compose.data_remote.ApiKeyStorage.USERS
import com.sopt.now.compose.data.dto.response.ResponseUserListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresApiService {
    @GET("$API/$USERS")
    suspend fun getUserList(
        @Query(PAGE) page: Int
    ): Response<ResponseUserListDto>
}