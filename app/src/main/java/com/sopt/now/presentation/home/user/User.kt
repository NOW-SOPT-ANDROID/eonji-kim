package com.sopt.now.presentation.home.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val pw: String,
    val nickname: String,
    val tel: String,
) : Parcelable
