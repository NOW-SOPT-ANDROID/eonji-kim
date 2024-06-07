package com.sopt.now.compose.presentation.myPage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.component.text.TextWithTitle
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.util.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@Composable
fun MyPageScreen(myPageViewModel: MyPageViewModel = viewModel()) {
    // 상태 관리를 위한 state 변수 선언
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var memberId by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var userNickname by remember { mutableStateOf("") }
    var userTel by remember { mutableStateOf("") }

    LaunchedEffect(myPageViewModel.userInfoUiState, lifecycleOwner) {
        myPageViewModel.userInfoUiState.flowWithLifecycle(lifecycleOwner.lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    with(it.data.data) {
                        memberId = "${myPageViewModel.getUserMemberId()}번"
                        userNickname = nickname
                        userId = authenticationId
                        userTel = phone
                    }
                }

                is UiState.Failure -> Toast.makeText(
                    context,
                    "유저조회 실패 : ${it.errorMessage}",
                    Toast.LENGTH_SHORT
                ).show()

                is UiState.Loading -> Timber.d("로딩중")
            }
        }.launchIn(lifecycleOwner.lifecycleScope)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        // Profile img
        Image(
            painter = painterResource(id = R.drawable.img_user_profile_dororo),
            contentDescription = "user profile img",
            modifier = Modifier
                .size(120.dp)
                .aspectRatio(1f / 1f),
        )
        // Nickname
        Text(
            text = userNickname,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, bottom = 25.dp)
        )
        // memberId
        TextWithTitle(stringResource(id = R.string.text_my_page_member_id_title), memberId, 40.dp)
        // Id
        TextWithTitle(stringResource(id = R.string.tf_login_id_title), userId, 40.dp)
        // Tel
        TextWithTitle(stringResource(id = R.string.tf_sign_tel_title), userTel)
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    NOWSOPTAndroidTheme {
        MyPageScreen()
    }
}