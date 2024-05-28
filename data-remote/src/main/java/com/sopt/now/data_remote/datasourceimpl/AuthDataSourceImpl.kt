package com.sopt.now.data_remote.datasourceimpl

import com.sopt.now.data.datasource.AuthDataSource
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import com.sopt.now.data.dto.response.ResponseSignDto
import com.sopt.now.data_remote.api.AuthApiService
import retrofit2.Response
import javax.inject.Inject

class AuthDataSourceImpl @Inject
constructor(
    private val authApiService: AuthApiService
) : AuthDataSource {
    override suspend fun postSign(requestSignDto: RequestSignDto): Response<ResponseSignDto> =
        authApiService.postSign(requestSignDto)

    override suspend fun postLogin(requestLoginDto: RequestLoginDto): Response<ResponseLoginDto> =
        authApiService.postLogin(requestLoginDto)
}