package com.sopt.now.compose.presentation.login

import android.app.Activity
import android.content.Intent
import android.widget.Toast
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
import com.sopt.now.compose.component.textfield.TextFieldWithTitle
import com.sopt.now.compose.presentation.home.HomeActivity
import com.sopt.now.compose.presentation.sign.SignActivity
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.White
import com.sopt.now.compose.ui.theme.YellowMain
import com.sopt.now.compose.util.KeyStorage.ERROR_LOGIN_ID_PW
import com.sopt.now.compose.util.UiState
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
                if (result.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.toast_login_sign_success), Toast.LENGTH_SHORT
                    ).show()
                } else Toast.makeText(
                    context,
                    context.getString(R.string.toast_login_sign_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    LaunchedEffect(loginViewModel.postLogin, lifecycleOwner) {
        loginViewModel.postLogin.flowWithLifecycle(lifecycleOwner.lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    Toast.makeText(context, it.data.toString(), Toast.LENGTH_SHORT).show()
                    Intent(context, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }.let { context.startActivity(it) }
                }

                is UiState.Failure -> {
                    when (it.errorMessage) {
                        ERROR_LOGIN_ID_PW -> Toast.makeText(
                            context,
                            context.getString(R.string.toast_login_fail),
                            Toast.LENGTH_SHORT
                        ).show()

                        else -> Toast.makeText(
                            context,
                            "로그인 실패 : ${it.errorMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
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
            "Welcome to SOPT",
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 50.dp, bottom = 100.dp)
        )

        // Id
        TextFieldWithTitle("ID", id, { newId -> id = newId }, "아이디를 입력해주세요")
        // Pw
        TextFieldWithTitle("비밀번호",
            pw,
            { newPw -> pw = newPw },
            "비밀번호를 입력해주세요",
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
            Text("로그인 하기", color = White, fontSize = 17.sp)
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
            Text("회원가입 하기", color = White, fontSize = 17.sp)
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