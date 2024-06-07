package com.sopt.now.compose.feature.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.core_ui.view.UiState
import com.sopt.now.compose.domain.entity.UserEntity
import com.sopt.now.compose.domain.repository.AuthRepository
import com.sopt.now.compose.feature.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.compose.feature.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.compose.feature.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.compose.feature.KeyStorage.ERROR_SIGN_TEL
import com.sopt.now.compose.feature.Regex.PW_REGEX
import com.sopt.now.compose.feature.Regex.TEL_REGEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject
constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _signUiState = MutableSharedFlow<UiState<Any>>()
    val signUiState: SharedFlow<UiState<Any>> = _signUiState

    fun postSign(user: UserEntity) = viewModelScope.launch {
        _signUiState.emit(UiState.Loading)

        if (checkSignValid(user) == null) {
            authRepository.postSign(user).collectLatest {
                if (it != null) _signUiState.emit(UiState.Success(it)) else UiState.Failure("null")
            }
        } else {
            _signUiState.emit(UiState.Failure(checkSignValid(user).toString()))
        }
    }

    private fun checkSignValid(userEntity: UserEntity): String? {
        return when {
            !getSignIdValid(userEntity.id) -> ERROR_SIGN_ID
            !getSignPwValid(userEntity.pw) -> ERROR_SIGN_PW
            !getSignNicknameValid(userEntity.nickname) -> ERROR_SIGN_NICKNAME
            !getSignTelValid(userEntity.tel) -> ERROR_SIGN_TEL
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

    private companion object {
        private const val MIN_ID_LEN = 6
        private const val MAX_ID_LEN = 10
    }
}