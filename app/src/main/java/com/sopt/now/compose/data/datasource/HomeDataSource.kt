package com.sopt.now.compose.data.datasource

import com.sopt.now.compose.data.dto.response.ResponseUserInfoDto

interface HomeDataSource {
    suspend fun getUserInfo(memberId: Int): ResponseUserInfoDto
}