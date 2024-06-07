package com.sopt.now.compose.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDto(
    @SerialName("code")
    val code: Int = -1,
    @SerialName("message")
    val message: String = "",
)