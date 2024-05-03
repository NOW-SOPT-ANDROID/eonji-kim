package com.sopt.now.compose.presentation.myPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.response.ResponseUserInfoDto
import com.sopt.now.compose.util.MainApplication
import com.sopt.now.compose.util.UiState
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val _getUserInfo = MutableLiveData<UiState<ResponseUserInfoDto>>(UiState.Loading)
    val getUserInfo: MutableLiveData<UiState<ResponseUserInfoDto>> = _getUserInfo

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        runCatching { ServicePool.authServiceApi.getUserInfo(getUserMemberId()) }.fold(
            { _getUserInfo.value = UiState.Success(it) },
            { _getUserInfo.value = UiState.Failure(it.message.toString()) }
        )
    }

    fun getUserMemberId(): Int = MainApplication.prefs.getUser()
}