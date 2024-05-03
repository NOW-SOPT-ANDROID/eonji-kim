package com.sopt.now.compose.presentation.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.request.RequestSignDto
import com.sopt.now.compose.data.dto.response.ResponseSignDto
import com.sopt.now.compose.model.User
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_TEL
import com.sopt.now.compose.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignViewModel : ViewModel() {
    private val _postSign = MutableStateFlow<UiState<Any?>>(UiState.Loading)
    val postSign: StateFlow<UiState<Any?>> = _postSign

    fun postSign(user: User) = viewModelScope.launch {
        if (checkSignValid(user) == null) {
            runCatching {
                ServicePool.authServiceApi.postSign(
                    RequestSignDto(user.id, user.pw, user.nickname, user.tel)
                )
            }.fold(
                {
                    if (it.code() == 201) {
                        _postSign.value = UiState.Success( "회원가입 성공! 유저의 ID는 ${it.headers()["location"]} 입니둥")
                    } else _postSign.value = UiState.Failure("${it.errorBody()?.string()?.split("\"")?.get(5)}")
                },
                { _postSign.value = UiState.Failure(it.message.toString()) }
            )
        } else {
            _postSign.value = UiState.Failure(checkSignValid(user).toString())
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

    companion object {
        const val MIN_ID_LEN = 6
        const val MAX_ID_LEN = 10
    }
}