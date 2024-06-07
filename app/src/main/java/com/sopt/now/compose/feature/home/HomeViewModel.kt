package com.sopt.now.compose.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.core_ui.view.UiState
import com.sopt.now.compose.domain.entity.FriendEntity
import com.sopt.now.compose.domain.entity.UserEntity
import com.sopt.now.compose.domain.repository.ReqresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(
    private val reqresRepository: ReqresRepository,
) : ViewModel() {
    val mockUserListEntity = listOf(
        UserEntity(
            id = "test",
            pw = "test",
            nickname = "기먼지",
            tel = "010-2002-0625"
        )
    )

    private val _friendEntityUiState =
        MutableStateFlow<UiState<List<FriendEntity>>>(UiState.Loading)
    val friendEntityUiState: StateFlow<UiState<List<FriendEntity>>> = _friendEntityUiState

    init {
        getUserList(2)
    }

    private fun getUserList(page: Int) = viewModelScope.launch {
        reqresRepository.getUserList(page).collectLatest {
            if (it != null) _friendEntityUiState.value =
                UiState.Success(it) else UiState.Failure("null")
        }
        _friendEntityUiState.value = UiState.Loading
    }
}