package com.sopt.now.compose

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.White
import com.sopt.now.compose.ui.theme.YellowMain
import com.sopt.now.compose.util.KeyStorage.USER_AGE
import com.sopt.now.compose.util.KeyStorage.USER_ID
import com.sopt.now.compose.util.KeyStorage.USER_NICKNAME
import com.sopt.now.compose.util.KeyStorage.USER_PW

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginContainer()
                }
            }
        }
    }
}

@Composable
fun LoginContainer() {
    // 상태 관리를 위한 state 변수 선언
    val context = LocalContext.current
    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var userPw by remember { mutableStateOf("") }
    var userNickname by remember { mutableStateOf("") }
    var userAge by remember { mutableStateOf("") }

    val getResult =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.run {
                if (result.resultCode == RESULT_OK) {
                    userId = getStringExtra(USER_ID).toString()
                    userPw = getStringExtra(USER_PW).toString()
                    userNickname = getStringExtra(USER_NICKNAME).toString()
                    userAge = getStringExtra(USER_AGE).toString()

                    Toast.makeText(context, "회원가입에 성공하였습니다!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        TextLoginTitle()

        // Id
        TextLoginFieldTitle("ID")
        OutlinedTextField(
            value = id,
            onValueChange = { newValues -> id = newValues },
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
            onValueChange = { newValues -> pw = newValues },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            label = { Text("비밀번호를 입력해주세요") },
            visualTransformation = VisualTransformation.None,
            singleLine = true,
        )

        Spacer(modifier = Modifier.weight(1f))

        // LoginBtn
        Button(
            onClick = {
                if (id.trim().isNotEmpty() && id == userId && pw.trim()
                        .isNotEmpty() && pw == userPw
                ) {
                    Toast.makeText(context, "로그인에 성공하였습니다!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(context, MainActivity::class.java).apply {
                        putExtras(
                            bundleOf(
                                "id" to userId,
                                "pw" to userPw,
                                "nickname" to userNickname,
                                "age" to userAge
                            )
                        )
                    }
                    context.startActivity(intent)
                } else Toast.makeText(context, "알맞은 ID와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            },
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

@Composable
private fun TextLoginTitle() {
    Text(
        "Welcome to SOPT",
        fontSize = 30.sp,
        modifier = Modifier.padding(top = 50.dp, bottom = 100.dp)
    )
}

@Composable
fun TextLoginFieldTitle(name: String) {
    Text(
        name,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp
    )
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NOWSOPTAndroidTheme {
        LoginContainer()
    }
}