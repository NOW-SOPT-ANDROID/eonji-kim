package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.FriendEntity
import kotlinx.coroutines.flow.Flow

interface ReqresRepository {
    suspend fun getUserList(page: Int): Flow<List<FriendEntity>?>
}