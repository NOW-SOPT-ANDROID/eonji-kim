package com.sopt.now.compose.data_remote.datasourceimpl

import com.sopt.now.compose.data.datasource.HomeDataSource
import com.sopt.now.compose.data.dto.response.ResponseUserInfoDto
import com.sopt.now.compose.data_remote.api.AuthApiService
import javax.inject.Inject

class HomeDataSourceImpl @Inject
constructor(
    private val authApiService: AuthApiService
) : HomeDataSource {
    override suspend fun getUserInfo(memberId: Int): ResponseUserInfoDto =
        authApiService.getUserInfo(memberId)
}