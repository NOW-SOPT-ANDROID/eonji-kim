package com.sopt.now.data.api

import com.sopt.now.data.KeyStorage.JOIN
import com.sopt.now.data.KeyStorage.LOGIN
import com.sopt.now.data.KeyStorage.MEMBER
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import com.sopt.now.data.dto.response.ResponseSignDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServiceApi {
    @POST("$MEMBER/$JOIN")
    suspend fun postSign(
        @Body requestSignDto: RequestSignDto,
    ): Response<ResponseSignDto>

    @POST("$MEMBER/$LOGIN")
    suspend fun postLogin(
        @Body requestLoginDto: RequestLoginDto
    ): Response<ResponseLoginDto>
}