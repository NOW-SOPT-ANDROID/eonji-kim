package com.sopt.now.compose.feature.login

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.core_ui.component.textfield.TextFieldWithTitle
import com.sopt.now.compose.feature.home.HomeActivity
import com.sopt.now.compose.feature.sign.SignActivity
import com.sopt.now.compose.core_ui.theme.GreenMain
import com.sopt.now.compose.core_ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.core_ui.theme.White
import com.sopt.now.compose.core_ui.theme.YellowMain
import com.sopt.now.compose.feature.KeyStorage.ERROR_LOGIN_ID_PW
import com.sopt.now.compose.core_ui.view.UiState
import com.sopt.now.compose.core_ui.util.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    // 상태 관리를 위한 state 변수 선언
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var pwVisibility by remember { mutableStateOf(false) }
    val pwIcon =
        painterResource(id = if (pwVisibility) R.drawable.ic_pw_visible else R.drawable.ic_pw_invisible)

    val getResult =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.run {
                if (result.resultCode != Activity.RESULT_OK) context.toast(context.getString(R.string.toast_login_sign_fail))
            }
        }

    LaunchedEffect(loginViewModel.loginUiState, lifecycleOwner) {
        loginViewModel.loginUiState.flowWithLifecycle(lifecycleOwner.lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    context.toast(it.data.toString())
                    Intent(context, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }.let(context::startActivity)
                }

                is UiState.Failure -> {
                    when (it.errorMessage) {
                        ERROR_LOGIN_ID_PW -> context.toast(context.getString(R.string.toast_login_fail))
                        else -> context.toast("로그인 실패 : ${it.errorMessage}")
                    }
                }

                is UiState.Loading -> Timber.d("로딩중")
            }
        }.launchIn(lifecycleOwner.lifecycleScope)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        Text(
            stringResource(id = R.string.text_login_title),
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 50.dp, bottom = 100.dp)
        )

        // Id
        TextFieldWithTitle(
            stringResource(id = R.string.tf_login_id_title),
            id,
            { newId -> id = newId },
            stringResource(id = R.string.tf_login_id_label)
        )
        // Pw
        TextFieldWithTitle(stringResource(id = R.string.tf_login_pw_title),
            pw,
            { newPw -> pw = newPw },
            stringResource(id = R.string.tf_login_pw_label),
            KeyboardType.Password,
            if (pwVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            {
                IconButton(onClick = { pwVisibility = !pwVisibility }) {
                    Icon(
                        painter = pwIcon,
                        contentDescription = "Visibility Icon"
                    )
                }
            })

        Spacer(modifier = Modifier.weight(1f))

        // LoginBtn
        Button(
            onClick = { loginViewModel.postLogin(id, pw) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = YellowMain),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(stringResource(id = R.string.btn_login_login), color = White, fontSize = 17.sp)
        }
        // SignBtn
        Button(
            onClick = { getResult.launch(Intent(context, SignActivity::class.java)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenMain),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(stringResource(id = R.string.btn_login_sign), color = White, fontSize = 17.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NOWSOPTAndroidTheme {
        LoginScreen()
    }
}