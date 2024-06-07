package com.sopt.now.compose.data.repositoryimpl

import com.sopt.now.compose.data.datasource.HomeDataSource
import com.sopt.now.compose.domain.entity.UserInfoEntity
import com.sopt.now.compose.domain.repository.HomeRepository
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
}