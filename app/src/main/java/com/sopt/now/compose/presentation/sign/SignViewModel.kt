package com.sopt.now.compose.presentation.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.request.RequestSignDto
import com.sopt.now.compose.model.User
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_TEL
import com.sopt.now.compose.util.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignViewModel : ViewModel() {
    private val _signUiState = MutableSharedFlow<UiState<Any>>()
    val signUiState: SharedFlow<UiState<Any>> = _signUiState

    fun postSign(user: User) = viewModelScope.launch {
        _signUiState.emit(UiState.Loading)

        if (checkSignValid(user) == null) {
            runCatching {
                ServicePool.authServiceApi.postSign(
                    RequestSignDto(user.id, user.pw, user.nickname, user.tel)
                )
            }.fold(
                {
                    if (it.code() == 201) {
                        _signUiState.emit(UiState.Success("회원가입 성공! 유저의 ID는 ${it.headers()["location"]} 입니둥"))
                    } else _signUiState.emit(
                        UiState.Failure("${it.errorBody()?.string()?.split("\"")?.get(5)}")
                    )
                },
                { _signUiState.emit(UiState.Failure(it.message.toString())) }
            )
        } else {
            _signUiState.emit(UiState.Failure(checkSignValid(user).toString()))
        }
    }

    private fun checkSignValid(user: User): String? {
        return when {
            !getSignIdValid(user.id) -> ERROR_SIGN_ID
            !getSignPwValid(user.pw) -> ERROR_SIGN_PW
            !getSignNicknameValid(user.nickname) -> ERROR_SIGN_NICKNAME
            !getSignTelValid(user.tel) -> ERROR_SIGN_TEL
            else -> null
        }
    }

    private fun getSignIdValid(userID: String) = userID.length in MIN_ID_LEN..MAX_ID_LEN
    private fun getSignPwValid(userPw: String): Boolean {
        Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$").let {
            return it.matcher(userPw).matches()
        }
    }

    private fun getSignNicknameValid(userNickname: String) = userNickname.isNotBlank()
    private fun getSignTelValid(userTel: String): Boolean {
        Pattern.compile("^010-\\d{4}-\\d{4}\$").let {
            return it.matcher(userTel).matches()
        }
    }

    private companion object {
        private const val MIN_ID_LEN = 6
        private const val MAX_ID_LEN = 10
    }
}