package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun postSign(user: UserEntity): Flow<Int?>
    suspend fun postLogin(id: String, pw: String): Flow<Int?>
    fun setMemberId(memberId: Int)
    fun getMemberId(): Int?
}