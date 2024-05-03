package com.sopt.now.compose.presentation.home

import android.widget.Toast
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

    LaunchedEffect(homeViewModel.getUserList, lifecycleOwner) {
        homeViewModel.getUserList.flowWithLifecycle(lifecycleOwner.lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    userData = homeViewModel.mockUserList
                    friendData = it.data ?: emptyList()
                }

                is UiState.Failure -> Toast.makeText(
                    context,
                    "유저 리스트 조회 실패 : ${it.errorMessage}",
                    Toast.LENGTH_SHORT
                ).show()

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
                R.drawable.img_user_profile_dororo,
                "user profile img",
                75.dp,
                user.nickname,
                18.sp,
                user.tel,
                14.sp,
                YellowMain,
                15.dp,
                12.dp,
            )
        }

        // Friend
        itemsIndexed(friendData) { index, friend ->
            TextWithRow(
                friend.avatar,
                "friend profile img",
                50.dp,
                "${friend.lastName}\n${friend.firstName}",
                16.sp,
                friend.email,
                13.sp,
                GreenMain,
                8.dp,
                10.dp,
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