package com.sopt.now.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.util.view.UiState
import com.sopt.now.data.ServicePool
import com.sopt.now.presentation.home.friend.Friend
import com.sopt.now.presentation.home.user.User
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

    private val _getUserList = MutableStateFlow<UiState<List<Friend>>>(UiState.Loading)
    val getUserList: StateFlow<UiState<List<Friend>>> = _getUserList

    init {
        getUserList(2)
    }

    private fun getUserList(page: Int) = viewModelScope.launch {
        runCatching {
            ServicePool.userServiceApi.getUserList(page).body()?.data?.map { it.toFriend() }
        }.fold(
            { _getUserList.value = UiState.Success(it ?: emptyList()) },
            { _getUserList.value = UiState.Failure(it.message.toString()) }
        )
    }
}