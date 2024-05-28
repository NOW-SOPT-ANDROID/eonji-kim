package com.sopt.now.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.entity.FriendEntity
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.ReqresRepository
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
    val mockUserList = listOf(
        UserEntity(
            id = "test",
            pw = "test",
            nickname = "기먼지",
            tel = "010-2002-0625"
        )
    )

    private val _getUserList = MutableStateFlow<UiState<List<FriendEntity>>>(UiState.Empty)
    val getUserList: StateFlow<UiState<List<FriendEntity>>> = _getUserList

    init {
        getUserList(2)
    }

    private fun getUserList(page: Int) = viewModelScope.launch {
        reqresRepository.getUserList(page).collectLatest {
            if (it != null) _getUserList.value = UiState.Success(it) else UiState.Failure("null")
        }
        _getUserList.value = UiState.Loading
    }
}