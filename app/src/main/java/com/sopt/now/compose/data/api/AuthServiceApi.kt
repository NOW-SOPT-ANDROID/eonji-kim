package com.sopt.now.compose.data.api

import com.sopt.now.compose.data.KeyStorage.INFO
import com.sopt.now.compose.data.KeyStorage.JOIN
import com.sopt.now.compose.data.KeyStorage.LOGIN
import com.sopt.now.compose.data.KeyStorage.MEMBER
import com.sopt.now.compose.data.KeyStorage.MEMBER_ID
import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.data.dto.request.RequestSignDto
import com.sopt.now.compose.data.dto.response.ResponseLoginDto
import com.sopt.now.compose.data.dto.response.ResponseSignDto
import com.sopt.now.compose.data.dto.response.ResponseUserInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("$MEMBER/$INFO")
    suspend fun getUserInfo(
        @Header(MEMBER_ID) memberId: Int
    ): ResponseUserInfoDto
}