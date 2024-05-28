package com.sopt.now.data.datasource

import com.sopt.now.data.dto.request.RequestUserPwDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.data.dto.response.ResponseUserPwDto

interface HomeDataSource {
    suspend fun getUserInfo(memberId: Int): ResponseUserInfoDto
    suspend fun patchUserPw(memberId: Int, requestUserPwDto: RequestUserPwDto): ResponseUserPwDto
}