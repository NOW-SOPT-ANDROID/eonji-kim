package com.sopt.now.compose.data.datasource

import com.sopt.now.compose.data.dto.response.ResponseUserListDto
import retrofit2.Response

interface ReqresDataSource {
    suspend fun getUserList(page: Int): Response<ResponseUserListDto>
}