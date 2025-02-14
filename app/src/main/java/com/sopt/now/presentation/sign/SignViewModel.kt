package com.sopt.now.presentation.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.util.view.UiState
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.request.RequestSignDto
import com.sopt.now.presentation.home.user.User
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_TEL
import com.sopt.now.presentation.util.Regex.PW_REGEX
import com.sopt.now.presentation.util.Regex.TEL_REGEX
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignViewModel : ViewModel() {
    private val _postSign = MutableLiveData<UiState<Any?>>(UiState.Loading)
    val postSign: MutableLiveData<UiState<Any?>> = _postSign

    fun postSign(user: User) = viewModelScope.launch {
        if (checkSignValid(user) == null) {
            runCatching {
                ServicePool.authServiceApi.postSign(
                    RequestSignDto(user.id, user.pw, user.nickname, user.tel)
                )
            }.fold(
                {
                    if (it.code() == 201) {
                        _postSign.value =
                            UiState.Success("회원가입 성공! 유저의 ID는 ${it.headers()["location"]} 입니둥")
                    } else _postSign.value =
                        UiState.Failure("${it.errorBody()?.string()?.split("\"")?.get(5)}")
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
        Pattern.compile(PW_REGEX).let {
            return it.matcher(userPw).matches()
        }
    }

    private fun getSignNicknameValid(userNickname: String) = userNickname.isNotBlank()
    private fun getSignTelValid(userTel: String): Boolean {
        Pattern.compile(TEL_REGEX).let {
            return it.matcher(userTel).matches()
        }
    }

    companion object {
        private const val MIN_ID_LEN = 6
        private const val MAX_ID_LEN = 10
    }
}