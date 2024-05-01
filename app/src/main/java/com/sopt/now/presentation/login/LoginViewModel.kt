package com.sopt.now.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.MainApplication
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.presentation.util.KeyStorage.ERROR_LOGIN_ID_PW
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _postLogin = MutableLiveData<LoginState>()
    val postLogin: MutableLiveData<LoginState> = _postLogin

    fun postLogin(id: String, pw: String) = viewModelScope.launch {
        if (checkLoginValid(id, pw)) {
            runCatching {
                ServicePool.authServiceApi.postLogin(RequestLoginDto(id, pw))
            }.fold(
                {
                    if (it.code() == 200) {
                        _postLogin.value =
                            LoginState(
                                true,
                                "로그인 성공! 유저의 ID는 ${it.headers()["location"]} 입니둥"
                            ).apply { setUserMemberId(it.headers()["location"]?.toInt() ?: -1) }
                    } else _postLogin.value =
                        LoginState(false, "${it.errorBody()?.string()?.split("\"")?.get(5)}")
                },
                { _postLogin.value = LoginState(false, "${it.message}") }
            )
        } else {
            _postLogin.value = LoginState(false, ERROR_LOGIN_ID_PW)
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