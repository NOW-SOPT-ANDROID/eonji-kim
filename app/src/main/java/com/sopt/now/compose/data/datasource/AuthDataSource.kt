package com.sopt.now.compose.data.datasource

import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.data.dto.request.RequestSignDto
import com.sopt.now.compose.data.dto.response.ResponseLoginDto
import com.sopt.now.compose.data.dto.response.ResponseSignDto
import retrofit2.Response

interface AuthDataSource {
    suspend fun postSign(requestSignDto: RequestSignDto): Response<ResponseSignDto>
    suspend fun postLogin(requestLoginDto: RequestLoginDto): Response<ResponseLoginDto>
}