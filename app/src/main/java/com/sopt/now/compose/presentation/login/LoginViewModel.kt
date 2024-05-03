package com.sopt.now.compose.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.data.dto.response.ResponseLoginDto
import com.sopt.now.compose.util.KeyStorage.ERROR_LOGIN_ID_PW
import com.sopt.now.compose.util.MainApplication
import com.sopt.now.compose.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _postLogin = MutableStateFlow<UiState<Any?>>(UiState.Loading)
    val postLogin: StateFlow<UiState<Any?>> = _postLogin

    fun postLogin(id: String, pw: String) = viewModelScope.launch {
        if (checkLoginValid(id, pw)) {
            runCatching {
                ServicePool.authServiceApi.postLogin(RequestLoginDto(id, pw))
            }.fold(
                {
                    if (it.code() == 200) {
                        _postLogin.value = UiState.Success("로그인 성공! 유저의 ID는 ${it.headers()["location"]} 입니둥").apply { setUserMemberId(it.headers()["location"]?.toInt() ?: -1) }
                    } else _postLogin.value = UiState.Failure("${it.errorBody()?.string()?.split("\"")?.get(5)}")

                },
                { _postLogin.value = UiState.Failure(it.message.toString()) }
            )
        } else {
            _postLogin.value = UiState.Failure(ERROR_LOGIN_ID_PW)
        }
    }

    private fun checkLoginValid(id: String, pw: String) =
        getLoginIdEmpty(id) && getLoginPwEmpty(pw)

    private fun getLoginIdEmpty(id: String) = id.isNotBlank()
    private fun getLoginPwEmpty(pw: String) = pw.isNotBlank()

    private fun setUserMemberId(memberId: Int) {
        MainApplication.prefs.setUser(memberId)
    }
}