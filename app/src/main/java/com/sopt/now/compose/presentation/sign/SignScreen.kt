package com.sopt.now.compose.presentation.sign

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
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
import androidx.compose.ui.text.font.FontWeight
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
import com.sopt.now.compose.model.User
import com.sopt.now.compose.presentation.login.LoginActivity
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.White
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.compose.util.KeyStorage.ERROR_SIGN_TEL
import com.sopt.now.compose.util.UiState
import com.sopt.now.compose.util.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@Composable
fun SignScreen(signViewModel: SignViewModel = viewModel()) {
    // 상태 관리를 위한 state 변수 선언
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var tel by remember { mutableStateOf("") }
    var pwVisibility by remember { mutableStateOf(false) }
    val pwIcon =
        painterResource(id = if (pwVisibility) R.drawable.ic_pw_visible else R.drawable.ic_pw_invisible)

    LaunchedEffect(signViewModel.signUiState, lifecycleOwner) {
        signViewModel.signUiState.flowWithLifecycle(lifecycleOwner.lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    context.toast(it.data.toString())
                    (context as? Activity)?.setResult(
                        RESULT_OK,
                        Intent(context, LoginActivity::class.java)
                    )
                    (context as? Activity)?.finish()
                }

                is UiState.Failure -> {
                    when (it.errorMessage) {
                        ERROR_SIGN_ID -> context.toast(context.getString(R.string.toast_sign_id_condition))
                        ERROR_SIGN_PW -> context.toast(context.getString(R.string.toast_sign_pw_condition))
                        ERROR_SIGN_NICKNAME -> context.toast(context.getString(R.string.toast_sign_nickname_condition))
                        ERROR_SIGN_TEL -> context.toast(context.getString(R.string.toast_sign_tel_condition))
                        else -> context.toast("회원가입 실패 : ${it.errorMessage}")
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
            stringResource(id = R.string.text_sign_titlte),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 50.dp, bottom = 40.dp)
        )

        // Id
        TextFieldWithTitle(stringResource(id = R.string.tf_login_id_title), id, { newId ->
            id = newId
        }, stringResource(id = R.string.tf_login_id_label))
        // Pw
        TextFieldWithTitle(stringResource(id = R.string.tf_login_pw_title),
            pw,
            { newPw ->
                pw = newPw
            },
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
        // Nickname
        TextFieldWithTitle(
            stringResource(id = R.string.tf_sign_nickname_title),
            nickname,
            { newNickname ->
                nickname = newNickname
            },
            stringResource(id = R.string.tf_sign_nickname_label)
        )
        // Tel
        TextFieldWithTitle(stringResource(id = R.string.tf_sign_tel_title), tel, { newAge ->
            tel = newAge
        }, stringResource(id = R.string.tf_sign_tel_label))

        Spacer(modifier = Modifier.weight(1f))

        // SignBtn
        Button(
            onClick = { signViewModel.postSign(User(id, pw, nickname, tel)) },
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
fun SignPreview() {
    NOWSOPTAndroidTheme {
        SignScreen()
    }
}