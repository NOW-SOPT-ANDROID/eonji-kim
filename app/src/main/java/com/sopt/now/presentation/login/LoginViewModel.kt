package com.sopt.now.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.util.view.UiState
import com.sopt.now.presentation.model.User
import com.sopt.now.presentation.util.KeyStorage.ERROR_LOGIN_ID_PW
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginValid = MutableSharedFlow<UiState<User>>()
    val loginValid: SharedFlow<UiState<User>> = _loginValid.asSharedFlow()

    fun checkLoginValid(id: String, pw: String, user: User) {
        viewModelScope.launch {
            val errorMessage = if (getLoginValid(id, pw, user)) null else ERROR_LOGIN_ID_PW
            _loginValid.emit(errorMessage?.let { UiState.Failure(it) } ?: UiState.Success(user))
        }
    }

    private fun getLoginValid(id: String, pw: String, user: User) =
        getLoginIdValid(id, user) && getLoginPwValid(pw, user) && getLoginIdEmpty(id) && getLoginPwEmpty(pw)

    private fun getLoginIdValid(id: String, user: User) = (id == user.id)
    private fun getLoginPwValid(pw: String, user: User) = (pw == user.pw)
    private fun getLoginIdEmpty(id: String) = id.isNotBlank()
    private fun getLoginPwEmpty(pw: String) = pw.isNotBlank()
}