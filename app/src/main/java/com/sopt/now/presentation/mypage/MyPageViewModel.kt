package com.sopt.now.presentation.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.MainApplication
import com.sopt.now.core.util.view.UiState
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val _getUserInfo = MutableLiveData<UiState<ResponseUserInfoDto>>()
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