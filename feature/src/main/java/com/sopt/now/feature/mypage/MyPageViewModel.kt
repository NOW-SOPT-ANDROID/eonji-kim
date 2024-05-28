package com.sopt.now.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.entity.UserInfoEntity
import com.sopt.now.domain.repository.AuthRepository
import com.sopt.now.domain.repository.HomeRepository
import com.sopt.now.feature.Regex.PW_REGEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject
constructor(
    private val homeRepository: HomeRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _getUserInfo = MutableStateFlow<UiState<UserInfoEntity>>(UiState.Loading)
    val getUserInfo: StateFlow<UiState<UserInfoEntity>> = _getUserInfo

    private val _patchUserPw = MutableSharedFlow<UiState<String>>()
    val patchUserPw: SharedFlow<UiState<String>> = _patchUserPw

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        getUserMemberId()?.let { memberId ->
            homeRepository.getUserInfo(memberId).collectLatest {
                if (it != null) _getUserInfo.value = UiState.Success(it) else UiState.Failure("null")
            }
        }
        _getUserInfo.value = UiState.Loading
    }

    fun patchUserPw(beforePw: String, afterPw: String, checkPw: String) = viewModelScope.launch {
        _patchUserPw.emit(UiState.Loading)

        if (checkPwValid(afterPw)) {
            getUserMemberId()?.let { memberId ->
                homeRepository.patchUserPw(memberId, beforePw, afterPw, checkPw)
                    .collectLatest {
                        if (it != null) _patchUserPw.emit(UiState.Success(it)) else UiState.Failure(
                            "null"
                        )
                    }
            }
        } else {
            _patchUserPw.emit(UiState.Failure("비밀번호를 8글자 이상, 숫자, 문자(a-z, A-Z), 특수문자 포함하여 입력해주세요."))
        }
    }

    private fun checkPwValid(userPw: String): Boolean {
        Pattern.compile(PW_REGEX).let {
            return it.matcher(userPw).matches()
        }
    }

    fun getUserMemberId(): Int? = authRepository.getMemberId()
}