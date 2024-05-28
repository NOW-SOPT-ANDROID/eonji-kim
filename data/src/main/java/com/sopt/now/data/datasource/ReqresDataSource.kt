package com.sopt.now.data.datasource

import com.sopt.now.data.dto.response.ResponseUserListDto
import retrofit2.Response

interface ReqresDataSource {
    suspend fun getUserList(page: Int): Response<ResponseUserListDto>
}