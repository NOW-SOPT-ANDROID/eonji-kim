package com.sopt.now.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
) : Parcelable