package com.sopt.now.compose.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.core_ui.view.UiState
import com.sopt.now.compose.domain.repository.AuthRepository
import com.sopt.now.compose.feature.KeyStorage.ERROR_LOGIN_ID_PW
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _loginUiState = MutableSharedFlow<UiState<Any>>()
    val loginUiState: SharedFlow<UiState<Any>> = _loginUiState

    fun postLogin(id: String, pw: String) = viewModelScope.launch {
        _loginUiState.emit(UiState.Loading)

        if (checkLoginValid(id, pw)) {
            authRepository.postLogin(id, pw).collectLatest {
                if (it != null) _loginUiState.emit(
                    UiState.Success(it).apply { setUserMemberId(it.toInt()) }) else UiState.Failure(
                    "null"
                )
            }
        } else {
            _loginUiState.emit(UiState.Failure(ERROR_LOGIN_ID_PW))
        }
    }

    private fun checkLoginValid(id: String, pw: String) =
        getLoginIdEmpty(id) && getLoginPwEmpty(pw)

    private fun getLoginIdEmpty(id: String) = id.isNotBlank()
    private fun getLoginPwEmpty(pw: String) = pw.isNotBlank()

    private fun setUserMemberId(memberId: Int) = authRepository.setMemberId(memberId)
}