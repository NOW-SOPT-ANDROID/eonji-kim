package com.sopt.now.compose.data_remote.datasourceimpl

import com.sopt.now.compose.data.datasource.ReqresDataSource
import com.sopt.now.compose.data.dto.response.ResponseUserListDto
import com.sopt.now.compose.data_remote.api.ReqresApiService
import retrofit2.Response
import javax.inject.Inject

class ReqresDataSourceImpl @Inject
constructor(
    private val reqresApiService: ReqresApiService,
) : ReqresDataSource {
    override suspend fun getUserList(page: Int): Response<ResponseUserListDto> =
        reqresApiService.getUserList(page)
}