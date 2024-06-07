package com.sopt.now.compose.feature.myPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.MainApplication
import com.sopt.now.compose.core_ui.view.UiState
import com.sopt.now.compose.domain.entity.UserInfoEntity
import com.sopt.now.compose.domain.repository.AuthRepository
import com.sopt.now.compose.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject
constructor(
    private val homeRepository: HomeRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _userInfoUiState = MutableStateFlow<UiState<UserInfoEntity>>(UiState.Loading)
    val userInfoUiState: StateFlow<UiState<UserInfoEntity>> = _userInfoUiState

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        getUserMemberId()?.let { memberId ->
            homeRepository.getUserInfo(memberId).collectLatest {
                if (it != null) _userInfoUiState.value =
                    UiState.Success(it) else UiState.Failure("null")
            }
        }
        _userInfoUiState.value = UiState.Loading
    }

    fun getUserMemberId(): Int? = authRepository.getMemberId()
}