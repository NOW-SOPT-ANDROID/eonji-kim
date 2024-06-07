package com.sopt.now.compose.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.util.KeyStorage.ERROR_LOGIN_ID_PW
import com.sopt.now.compose.util.MainApplication
import com.sopt.now.compose.util.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableSharedFlow<UiState<Any>>()
    val loginUiState: SharedFlow<UiState<Any>> = _loginUiState

    fun postLogin(id: String, pw: String) = viewModelScope.launch {
        _loginUiState.emit(UiState.Loading)

        if (isLoginBlank(id, pw)) {
            runCatching {
                ServicePool.authServiceApi.postLogin(RequestLoginDto(id, pw))
            }.fold(
                {
                    if (it.code() == 200) {
                        _loginUiState.emit(
                            UiState.Success("로그인 성공! 유저의 ID는 ${it.headers()["location"]} 입니둥")
                                .apply { setUserMemberId(it.headers()["location"]?.toInt() ?: -1) })
                    } else _loginUiState.emit(
                        UiState.Failure("${it.errorBody()?.string()?.split("\"")?.get(5)}")
                    )
                },
                { _loginUiState.emit(UiState.Failure(it.message.toString())) }
            )
        } else {
            _loginUiState.emit(UiState.Failure(ERROR_LOGIN_ID_PW))
        }
    }

    private fun isLoginBlank(id: String, pw: String) =
        getLoginIdEmpty(id) && getLoginPwEmpty(pw)

    private fun getLoginIdEmpty(id: String) = id.isNotBlank()
    private fun getLoginPwEmpty(pw: String) = pw.isNotBlank()

    private fun setUserMemberId(memberId: Int) {
        MainApplication.prefs.setUser(memberId)
    }
}