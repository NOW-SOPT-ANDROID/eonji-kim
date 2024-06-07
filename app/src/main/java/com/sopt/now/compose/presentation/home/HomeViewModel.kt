package com.sopt.now.compose.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.model.Friend
import com.sopt.now.compose.model.User
import com.sopt.now.compose.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val mockUserList = listOf(
        User(
            id = "test",
            pw = "test",
            nickname = "기먼지",
            tel = "010-2002-0625"
        )
    )

    private val _friendUiState = MutableStateFlow<UiState<List<Friend>>>(UiState.Loading)
    val friendUiState: StateFlow<UiState<List<Friend>>> = _friendUiState

    init {
        getUserList(2)
    }

    private fun getUserList(page: Int) = viewModelScope.launch {
        runCatching {
            ServicePool.userServiceApi.getUserList(page).body()?.data?.map { it.toFriend() }
        }.fold(
            {
                _friendUiState.value = UiState.Success(it ?: emptyList())
            },
            { _friendUiState.value = UiState.Failure(it.message.toString()) }
        )
    }
}