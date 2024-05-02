package com.sopt.now.compose.presentation.sign

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.component.textfield.TextFieldWithTitle
import com.sopt.now.compose.model.User
import com.sopt.now.compose.presentation.login.LoginActivity
import com.sopt.now.compose.presentation.sign.SignActivity.Companion.MAX_AGE_LEN
import com.sopt.now.compose.presentation.sign.SignActivity.Companion.MAX_ID_LEN
import com.sopt.now.compose.presentation.sign.SignActivity.Companion.MAX_PW_LEN
import com.sopt.now.compose.presentation.sign.SignActivity.Companion.MIN_AGE_LEN
import com.sopt.now.compose.presentation.sign.SignActivity.Companion.MIN_ID_LEN
import com.sopt.now.compose.presentation.sign.SignActivity.Companion.MIN_PW_LEN
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.White
import com.sopt.now.compose.util.KeyStorage.USER_DATA

@Composable
fun SignScreen() {
    // 상태 관리를 위한 state 변수 선언
    val context = LocalContext.current
    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isIdValid by remember { mutableStateOf(false) }
    var isPwValid by remember { mutableStateOf(false) }
    var isNicknameValid by remember { mutableStateOf(false) }
    var isAgeValid by remember { mutableStateOf(false) }
    var pwVisibility by remember { mutableStateOf(false) }
    val pwIcon =
        painterResource(id = if (pwVisibility) R.drawable.ic_pw_visible else R.drawable.ic_pw_invisible)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        Text(
            "SIGN UP",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 50.dp, bottom = 40.dp)
        )

        // Id
        TextFieldWithTitle("ID", id, { newId ->
            id = newId
            isIdValid = id.length in MIN_ID_LEN..MAX_ID_LEN
        }, "아이디를 입력해주세요")
        // Pw
        TextFieldWithTitle("비밀번호",
            pw,
            { newPw ->
                pw = newPw
                isPwValid = pw.length in MIN_PW_LEN..MAX_PW_LEN
            },
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
        // Nickname
        TextFieldWithTitle("닉네임", nickname, { newNickname ->
            nickname = newNickname
            isNicknameValid = nickname.isNotBlank()
        }, "닉네임을 입력해주세요")
        // Age
        TextFieldWithTitle("나이", age, { newAge ->
            age = newAge
            isAgeValid = age.trim().length in MIN_AGE_LEN..MAX_AGE_LEN
        }, "나이를 입력해주세요")

        Spacer(modifier = Modifier.weight(1f))

        // SignBtn
        Button(
            onClick = {
                when {
                    !isIdValid -> Toast.makeText(
                        context,
                        context.getString(R.string.toast_sign_id_condition),
                        Toast.LENGTH_SHORT
                    ).show()

                    !isPwValid -> Toast.makeText(
                        context,
                        context.getString(R.string.toast_sign_pw_condition),
                        Toast.LENGTH_SHORT
                    ).show()

                    !isNicknameValid -> Toast.makeText(
                        context,
                        context.getString(R.string.toast_sign_nickname_condition),
                        Toast.LENGTH_SHORT
                    ).show()

                    !isAgeValid -> Toast.makeText(
                        context,
                        context.getString(R.string.toast_sign_age_condition),
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        Intent(context, LoginActivity::class.java).apply {
                            putExtra(USER_DATA, User(id, pw, nickname, age))
                        }.let { (context as? Activity)?.setResult(RESULT_OK, it) }
                        (context as? Activity)?.finish()
                    }
                }
            },
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
fun SignPreview() {
    NOWSOPTAndroidTheme {
        SignScreen()
    }
}