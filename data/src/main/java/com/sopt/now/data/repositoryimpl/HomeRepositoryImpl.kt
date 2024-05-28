package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.HomeDataSource
import com.sopt.now.data.dto.request.RequestUserPwDto
import com.sopt.now.domain.entity.UserInfoEntity
import com.sopt.now.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject
constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun getUserInfo(memberId: Int): Flow<UserInfoEntity?> {
        return flow {
            val result =
                runCatching {
                    homeDataSource.getUserInfo(memberId).data.let {
                        UserInfoEntity(memberId, it.authenticationId, it.nickname, it.phone)
                    }
                }
            emit(result.getOrDefault(UserInfoEntity(-1, "", "", "")))
        }
    }

    override suspend fun patchUserPw(
        memberId: Int,
        beforePw: String,
        afterPw: String,
        checkPw: String
    ): Flow<String?> {
        return flow {
            val result =
                runCatching {
                    homeDataSource.patchUserPw(
                        memberId,
                        RequestUserPwDto(beforePw, afterPw, checkPw)
                    ).message
                }
            emit(result.getOrNull())
        }
    }
}