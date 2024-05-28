package com.sopt.now.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.MainApplication
import com.sopt.now.core.util.view.UiState
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.request.RequestUserPwDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.data.dto.response.ResponseUserPwDto
import com.sopt.now.presentation.util.Regex.PW_REGEX
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class MyPageViewModel : ViewModel() {
    private val _getUserInfo = MutableStateFlow<UiState<ResponseUserInfoDto>>(UiState.Loading)
    val getUserInfo: StateFlow<UiState<ResponseUserInfoDto>> = _getUserInfo

    private val _patchUserPw = MutableSharedFlow<UiState<ResponseUserPwDto>>()
    val patchUserPw: SharedFlow<UiState<ResponseUserPwDto>> = _patchUserPw

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        runCatching { ServicePool.authServiceApi.getUserInfo(getUserMemberId()) }.fold(
            { _getUserInfo.value = UiState.Success(it) },
            { _getUserInfo.value = UiState.Failure(it.message.toString()) }
        )
    }

    fun patchUserPw(beforePw: String, afterPw: String, checkPw: String) = viewModelScope.launch {
        _patchUserPw.emit(UiState.Loading)

        if (checkPwValid(afterPw)) {
            runCatching {
                ServicePool.authServiceApi.patchUserPw(
                    getUserMemberId(),
                    RequestUserPwDto(beforePw, afterPw, checkPw)
                )
            }.fold(
                { _patchUserPw.emit(UiState.Success(it)) },
                { _patchUserPw.emit(UiState.Failure(it.message.toString())) }
            )
        } else {
            _patchUserPw.emit(
                UiState.Failure("비밀번호를 8글자 이상, 숫자, 문자(a-z, A-Z), 특수문자 포함하여 입력해주세요.")
            )
        }
    }

    private fun checkPwValid(userPw: String): Boolean {
        Pattern.compile(PW_REGEX).let {
            return it.matcher(userPw).matches()
        }
    }

    fun getUserMemberId(): Int = MainApplication.prefs.getUser()
}