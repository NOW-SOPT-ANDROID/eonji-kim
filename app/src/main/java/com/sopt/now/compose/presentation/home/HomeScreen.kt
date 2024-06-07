package com.sopt.now.compose.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.component.text.TextWithRow
import com.sopt.now.compose.model.Friend
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.YellowMain
import com.sopt.now.compose.util.UiState
import com.sopt.now.compose.util.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    // 상태 관리를 위한 state 변수 선언
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var userData by remember { mutableStateOf(emptyList<User>()) }
    var friendData by remember { mutableStateOf(emptyList<Friend>()) }

    LaunchedEffect(homeViewModel.friendUiState, lifecycleOwner) {
        homeViewModel.friendUiState.flowWithLifecycle(lifecycleOwner.lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    userData = homeViewModel.mockUserList
                    friendData = it.data ?: emptyList()
                }

                is UiState.Failure -> context.toast("유저 리스트 조회 실패 : ${it.errorMessage}")
                is UiState.Loading -> Timber.d("로딩중")
            }
        }.launchIn(lifecycleOwner.lifecycleScope)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // User
        itemsIndexed(userData) { index, user ->
            TextWithRow(
                painter = R.drawable.img_user_profile_dororo,
                contentDescription = "user profile img",
                profileSize = 75.dp,
                name = user.nickname,
                nameSize = 18.sp,
                description = user.tel,
                descriptionSize = 14.sp,
                descriptionColor = YellowMain,
                rowVertical = 15.dp,
                spacerWidth = 12.dp,
            )
        }

        // Friend
        itemsIndexed(friendData) { index, friend ->
            TextWithRow(
                painter = friend.avatar,
                contentDescription = "friend profile img",
                profileSize = 50.dp,
                name = "${friend.lastName}\n${friend.firstName}",
                nameSize = 16.sp,
                description = friend.email,
                descriptionSize = 13.sp,
                descriptionColor = GreenMain,
                rowVertical = 8.dp,
                spacerWidth = 10.dp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NOWSOPTAndroidTheme {
        HomeScreen()
    }
}