package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getUserInfo(memberId: Int): Flow<UserInfoEntity?>
    suspend fun patchUserPw(
        memberId: Int,
        beforePw: String,
        afterPw: String,
        checkPw: String
    ): Flow<String?>
}