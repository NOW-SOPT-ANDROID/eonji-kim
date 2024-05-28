package com.sopt.now.data_remote.api

import com.sopt.now.data.dto.response.ResponseUserListDto
import com.sopt.now.data_remote.ApiKeyStorage.API
import com.sopt.now.data_remote.ApiKeyStorage.PAGE
import com.sopt.now.data_remote.ApiKeyStorage.USERS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresApiService {
    @GET("$API/$USERS")
    suspend fun getUserList(
        @Query(PAGE) page: Int
    ): Response<ResponseUserListDto>
}