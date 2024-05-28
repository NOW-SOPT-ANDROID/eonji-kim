package com.sopt.now.feature.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.AuthRepository
import com.sopt.now.feature.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.feature.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.feature.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.feature.KeyStorage.ERROR_SIGN_TEL
import com.sopt.now.feature.Regex.PW_REGEX
import com.sopt.now.feature.Regex.TEL_REGEX
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
    private val _postSign = MutableSharedFlow<UiState<Int>>()
    val postSign: SharedFlow<UiState<Int>> = _postSign

    fun postSign(user: UserEntity) = viewModelScope.launch {
        _postSign.emit(UiState.Loading)

        if (checkSignValid(user) == null) {
            authRepository.postSign(user).collectLatest {
                if (it != null) _postSign.emit(UiState.Success(it)) else UiState.Failure("null")
            }
        } else {
            _postSign.emit(UiState.Failure(checkSignValid(user).toString()))
        }
    }

    private fun checkSignValid(user: UserEntity): String? {
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