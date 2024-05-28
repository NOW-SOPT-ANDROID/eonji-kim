package com.sopt.now.data.datasource

import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import com.sopt.now.data.dto.response.ResponseSignDto
import retrofit2.Response

interface AuthDataSource {
    suspend fun postSign(requestSignDto: RequestSignDto): Response<ResponseSignDto>
    suspend fun postLogin(requestLoginDto: RequestLoginDto): Response<ResponseLoginDto>
}