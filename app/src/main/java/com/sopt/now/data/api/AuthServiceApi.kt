package com.sopt.now.data.api

import com.sopt.now.data.KeyStorage.INFO
import com.sopt.now.data.KeyStorage.JOIN
import com.sopt.now.data.KeyStorage.LOGIN
import com.sopt.now.data.KeyStorage.MEMBER
import com.sopt.now.data.KeyStorage.MEMBER_ID
import com.sopt.now.data.KeyStorage.PASSWORD
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignDto
import com.sopt.now.data.dto.request.RequestUserPwDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import com.sopt.now.data.dto.response.ResponseSignDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.data.dto.response.ResponseUserPwDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
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

    @PATCH("$MEMBER/$PASSWORD")
    suspend fun patchUserPw(
        @Header(MEMBER_ID) memberId: Int,
        @Body requestUserPwDto: RequestUserPwDto
    ): ResponseUserPwDto
}