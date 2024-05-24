package com.sopt.now.data.dto.response

import com.sopt.now.presentation.home.friend.Friend
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserListDto(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<ResponseUserListDataDto>,
    @SerialName("support")
    val support: ResponseUserListSupportDto
)

@Serializable
data class ResponseUserListDataDto(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("avatar")
    val avatar: String,
) {
    fun toFriend() = Friend(
        id,
        email,
        firstName,
        lastName,
        avatar
    )
}

@Serializable
data class ResponseUserListSupportDto(
    @SerialName("url")
    val url: String,
    @SerialName("text")
    val text: String,
)
