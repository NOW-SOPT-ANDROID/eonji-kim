package com.sopt.now.compose.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserInfoDto(
    @SerialName("code")
    val code: Int = -1,
    @SerialName("message")
    val message: String = "",
    @SerialName("data")
    val data: ResponseUserInfoDataDto,
)

@Serializable
data class ResponseUserInfoDataDto(
    @SerialName("authenticationId")
    val authenticationId: String = "",
    @SerialName("nickname")
    val nickname: String = "",
    @SerialName("phone")
    val phone: String = "",
)