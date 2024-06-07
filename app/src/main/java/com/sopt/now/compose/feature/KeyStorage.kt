package com.sopt.now.compose.feature

object KeyStorage {
    const val MEMBER_ID = "member_id"
    const val ERROR_LOGIN_ID_PW = "error_login_id_pw"
    const val ERROR_SIGN_ID = "error_sign_id"
    const val ERROR_SIGN_PW = "error_sign_pw"
    const val ERROR_SIGN_NICKNAME = "error_sign_nickname"
    const val ERROR_SIGN_TEL = "error_sign_tel"
}

object Regex {
    const val PW_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$"
    const val TEL_REGEX = "^010-\\d{4}-\\d{4}\$"
}