package com.sopt.now.compose.presentation

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.sopt.now.compose.R
import com.sopt.now.compose.component.textfield.TextFieldWithTitle
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
    var pwVisibility by remember { mutableStateOf(false) }
    val pwIcon =
        painterResource(id = if (pwVisibility) R.drawable.ic_pw_visible else R.drawable.ic_pw_invisible)

    val getResult =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.run {
                if (result.resultCode == RESULT_OK) {
                    userId = getStringExtra(USER_ID).toString()
                    userPw = getStringExtra(USER_PW).toString()
                    userNickname = getStringExtra(USER_NICKNAME).toString()
                    userAge = getStringExtra(USER_AGE).toString()

                    Toast.makeText(context, "회원가입에 성공하였습니다!", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(context, "회원가입에 실패하였습니다!", Toast.LENGTH_SHORT).show()
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
            onClick = {
                if (id.isNotBlank() && id == userId && pw.isNotBlank() && pw == userPw
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
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NOWSOPTAndroidTheme {
        LoginContainer()
    }
}