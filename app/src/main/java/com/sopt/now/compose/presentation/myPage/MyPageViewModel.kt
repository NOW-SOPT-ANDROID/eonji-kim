package com.sopt.now.compose.presentation.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.response.ResponseUserInfoDto
import com.sopt.now.compose.util.MainApplication
import com.sopt.now.compose.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val _userInfoUiState = MutableStateFlow<UiState<ResponseUserInfoDto>>(UiState.Loading)
    val userInfoUiState: StateFlow<UiState<ResponseUserInfoDto>> = _userInfoUiState

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        runCatching { ServicePool.authServiceApi.getUserInfo(getUserMemberId()) }.fold(
            { _userInfoUiState.value = UiState.Success(it) },
            { _userInfoUiState.value = UiState.Failure(it.message.toString()) }
        )
    }

    fun getUserMemberId(): Int = MainApplication.prefs.getUser()
}