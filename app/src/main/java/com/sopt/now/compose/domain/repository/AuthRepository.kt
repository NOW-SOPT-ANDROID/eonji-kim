package com.sopt.now.compose.domain.repository

import com.sopt.now.compose.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun postSign(userEntity: UserEntity): Flow<Int?>
    suspend fun postLogin(id: String, pw: String): Flow<Int?>
    fun setMemberId(memberId: Int)
    fun getMemberId(): Int?
}