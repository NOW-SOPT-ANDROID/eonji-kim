package com.sopt.now.compose.data.repositoryimpl

import com.sopt.now.compose.data.datasource.ReqresDataSource
import com.sopt.now.compose.domain.entity.FriendEntity
import com.sopt.now.compose.domain.repository.ReqresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReqresRepositoryImpl @Inject
constructor(
    private val reqresDataSource: ReqresDataSource,
) : ReqresRepository {
    override suspend fun getUserList(page: Int): Flow<List<FriendEntity>?> {
        return flow {
            val result =
                runCatching {
                    reqresDataSource.getUserList(page).body()?.data?.map { it.toFriendEntity() }
                }
            emit(result.getOrDefault(emptyList()))
        }
    }
}