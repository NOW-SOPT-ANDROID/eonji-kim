package com.sopt.now.data_remote.datasourceimpl

import com.sopt.now.data.datasource.HomeDataSource
import com.sopt.now.data.dto.request.RequestUserPwDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.data.dto.response.ResponseUserPwDto
import com.sopt.now.data_remote.api.AuthApiService
import javax.inject.Inject

class HomeDataSourceImpl @Inject
constructor(
    private val authApiService: AuthApiService
) : HomeDataSource {
    override suspend fun getUserInfo(memberId: Int): ResponseUserInfoDto =
        authApiService.getUserInfo(memberId)

    override suspend fun patchUserPw(
        memberId: Int,
        requestUserPwDto: RequestUserPwDto
    ): ResponseUserPwDto = authApiService.patchUserPw(memberId, requestUserPwDto)
}