package com.sopt.now.presentation.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.util.view.UiState
import com.sopt.now.presentation.model.User
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_AGE
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_PW
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignViewModel : ViewModel() {
    private val _signValid = MutableSharedFlow<UiState<User>>()
    val signValid: SharedFlow<UiState<User>> = _signValid.asSharedFlow()

    fun checkSignValid(user: User) {
        viewModelScope.launch {
            val errorMessage = when {
                !getSignIdValid(user.id) -> ERROR_SIGN_ID
                !getSignPwValid(user.pw) -> ERROR_SIGN_PW
                !getSignNicknameValid(user.nickname) -> ERROR_SIGN_NICKNAME
                !getSignAgeValid(user.age) -> ERROR_SIGN_AGE
                else -> null
            }
            _signValid.emit(errorMessage?.let { UiState.Failure(it) } ?: UiState.Success(user))
        }
    }

    private fun getSignIdValid(userID: String) = userID.length in MIN_ID_LEN..MAX_ID_LEN
    private fun getSignPwValid(userPw: String) = userPw.length in MIN_PW_LEN..MAX_PW_LEN
    private fun getSignNicknameValid(userNickname: String) = userNickname.isNotEmpty()
    private fun getSignAgeValid(userAge: String) = userAge.length in MIN_AGE_LEN..MAX_AGE_LEN

    companion object {
        const val MIN_ID_LEN = 6
        const val MAX_ID_LEN = 10
        const val MIN_PW_LEN = 8
        const val MAX_PW_LEN = 12
        const val MIN_AGE_LEN = 1
        const val MAX_AGE_LEN = 3
    }
}