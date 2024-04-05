package com.sopt.now.compose

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.White
import com.sopt.now.compose.util.KeyStorage.USER_AGE
import com.sopt.now.compose.util.KeyStorage.USER_ID
import com.sopt.now.compose.util.KeyStorage.USER_NICKNAME
import com.sopt.now.compose.util.KeyStorage.USER_PW

class SignActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignContainer()
                }
            }
        }
    }
}

@Composable
fun SignContainer() {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        TextSignTitle()

        // Id
        TextLoginFieldTitle("ID")
        OutlinedTextField(
            value = id,
            onValueChange = { newId ->
                id = newId
                isIdValid = id.trim().length in 6..10
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            label = { Text("아이디를 입력해주세요") },
            singleLine = true,
        )
        // Pw
        TextLoginFieldTitle("비밀번호")
        OutlinedTextField(
            value = pw,
            onValueChange = { newPw ->
                pw = newPw
                isPwValid = pw.trim().length in 8..12
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            label = { Text("비밀번호를 입력해주세요") },
            visualTransformation = VisualTransformation.None,
            singleLine = true,
        )
        // Nickname
        TextLoginFieldTitle("닉네임")
        OutlinedTextField(
            value = nickname,
            onValueChange = { newNickname ->
                nickname = newNickname
                isNicknameValid = nickname.trim().isNotEmpty()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            label = { Text("닉네임을 입력해주세요") },
            singleLine = true,
        )
        // Age
        TextLoginFieldTitle("나이")
        OutlinedTextField(
            value = age,
            onValueChange = { newAge ->
                age = newAge
                isAgeValid = age.trim().length in 1..3
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            label = { Text("나이를 입력해주세요") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.weight(1f))

        // SignBtn
        Button(
            onClick = {
                when {
                    !isIdValid -> Toast.makeText(
                        context,
                        "ID를 6글자 이상 10글자 이하로 입력해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()

                    !isPwValid -> Toast.makeText(
                        context,
                        "비밀번호를 8글자 이상 12글자 이하로 입력해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()

                    !isNicknameValid -> Toast.makeText(
                        context,
                        "닉네임을 공백 제외 1글자 이상으로 입력해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()

                    !isAgeValid -> Toast.makeText(
                        context,
                        "나이를 1글자 이상 3글자 이하로 입력해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        val intent = Intent(context, LoginActivity::class.java).apply {
                            putExtras(
                                bundleOf(
                                    USER_ID to id,
                                    USER_PW to pw,
                                    USER_NICKNAME to nickname,
                                    USER_AGE to age
                                )
                            )
                        }
                        (context as? Activity)?.setResult(RESULT_OK, intent)
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

@Composable
private fun TextSignTitle() {
    Text(
        "SIGN UP",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 50.dp, bottom = 40.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SignPreview() {
    NOWSOPTAndroidTheme {
        SignContainer()
    }
}