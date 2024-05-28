package com.sopt.now.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserPwDto(
    @SerialName("previousPassword")
    val previousPassword: String,
    @SerialName("newPassword")
    val newPassword: String,
    @SerialName("newPasswordVerification")
    val newPasswordVerification: String,
)
