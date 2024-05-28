package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.AuthDataSource
import com.sopt.now.data.datasource.SharedPreferenceDataSource
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignDto
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject
constructor(
    private val sharedPreferencesDataSource: SharedPreferenceDataSource,
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun postSign(user: UserEntity): Flow<Int?> {
        return flow {
            val result =
                runCatching {
                    authDataSource.postSign(
                        RequestSignDto(
                            user.id,
                            user.pw,
                            user.nickname,
                            user.tel
                        )
                    ).headers()["location"]?.toInt()
                }
            emit(result.getOrDefault(-1))
        }
    }

    override suspend fun postLogin(id: String, pw: String): Flow<Int?> {
        return flow {
            val result =
                runCatching {
                    authDataSource.postLogin(RequestLoginDto(id, pw)).headers()["location"]?.toInt()
                }
            emit(result.getOrDefault(-1))
        }
    }

    override fun setMemberId(memberId: Int) {
        sharedPreferencesDataSource.memberId = memberId
    }

    override fun getMemberId(): Int? = sharedPreferencesDataSource.memberId
}