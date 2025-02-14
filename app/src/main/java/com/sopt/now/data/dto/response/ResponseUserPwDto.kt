package com.sopt.now.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserPwDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
)