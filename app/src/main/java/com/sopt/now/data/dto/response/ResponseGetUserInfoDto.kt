package com.sopt.now.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetUserInfoDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: ResponseGetUserInfoDataDto,
)

@Serializable
data class ResponseGetUserInfoDataDto(
    @SerialName("authenticationId")
    val authenticationId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("phone")
    val phone: String,
)