package com.sopt.now.compose.domain.repository

import com.sopt.now.compose.domain.entity.FriendEntity
import kotlinx.coroutines.flow.Flow

interface ReqresRepository {
    suspend fun getUserList(page: Int): Flow<List<FriendEntity>?>
}