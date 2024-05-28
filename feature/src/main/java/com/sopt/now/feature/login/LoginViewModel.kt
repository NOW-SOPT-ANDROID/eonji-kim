package com.sopt.now.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.repository.AuthRepository
import com.sopt.now.feature.KeyStorage.ERROR_LOGIN_ID_PW
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
    private val _postLogin = MutableSharedFlow<UiState<Int>>()
    val postLogin: SharedFlow<UiState<Int>> = _postLogin

    fun postLogin(id: String, pw: String) = viewModelScope.launch {
        _postLogin.emit(UiState.Loading)

        if (checkLoginValid(id, pw)) {
            authRepository.postLogin(id, pw).collectLatest {
                if (it != null) _postLogin.emit(
                    UiState.Success(it).apply { setUserMemberId(it.toInt()) }) else UiState.Failure(
                    "null"
                )
            }
        } else {
            _postLogin.emit(UiState.Failure(ERROR_LOGIN_ID_PW))
        }
    }

    private fun checkLoginValid(id: String, pw: String) =
        getLoginIdEmpty(id) && getLoginPwEmpty(pw)

    private fun getLoginIdEmpty(id: String) = id.isNotBlank()
    private fun getLoginPwEmpty(pw: String) = pw.isNotBlank()

    private fun setUserMemberId(memberId: Int) = authRepository.setMemberId(memberId)
}