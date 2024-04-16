package com.sopt.now.compose.presentation

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.component.text.TextWithTitle
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.Grey700
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.util.KeyStorage.USER_AGE
import com.sopt.now.compose.util.KeyStorage.USER_ID
import com.sopt.now.compose.util.KeyStorage.USER_NICKNAME
import com.sopt.now.compose.util.KeyStorage.USER_PW

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContainer()
                }
            }
        }
    }
}

@Composable
fun MainContainer() {
    // 상태 관리를 위한 state 변수 선언
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 2000) {
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "종료하시려면 한 번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Img
            Image(
                painter = painterResource(id = R.drawable.img_main_profile),
                contentDescription = "user profile img",
                modifier = Modifier
                    .size(90.dp)
                    .aspectRatio(1f / 1f),
            )
            // Nickname
            Text(
                text = (context as? Activity)?.intent?.getStringExtra(USER_NICKNAME) ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                "·",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp),
                color = Grey700
            )
            // Age
            Text(
                text = (context as? Activity)?.intent?.getStringExtra(USER_AGE) ?: "",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp),
                color = Grey700
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 50.dp),
            color = GreenMain,
            thickness = 3.dp
        )

        // Id
        TextWithTitle("ID", (context as? Activity)?.intent?.getStringExtra(USER_ID) ?: "", 40.dp)
        // Pw
        TextWithTitle("비밀번호", (context as? Activity)?.intent?.getStringExtra(USER_PW) ?: "")
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NOWSOPTAndroidTheme {
        MainContainer()
    }
}