package com.sopt.now.compose.domain.repository

import com.sopt.now.compose.domain.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getUserInfo(memberId: Int): Flow<UserInfoEntity?>
}