package com.sopt.now.compose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.Grey600
import com.sopt.now.compose.ui.theme.Grey700
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

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
            (context as? Activity)?.intent?.getStringExtra("nickname")?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Text(
                "·",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp),
                color = Grey700
            )
            // Age
            (context as? Activity)?.intent?.getStringExtra("age")?.let {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Grey700
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 50.dp),
            color = GreenMain,
            thickness = 3.dp
        )

        // Id
        TextMainFieldTitle("ID")
        (context as? Activity)?.intent?.getStringExtra("id")?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 40.dp),
                color = Grey600
            )
        }

        // Pw
        TextMainFieldTitle("비밀번호")
        (context as? Activity)?.intent?.getStringExtra("pw")?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                color = Grey600
            )
        }
    }
}

@Composable
private fun TextMainFieldTitle(name: String) {
    Text(
        name,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 24.sp
    )
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NOWSOPTAndroidTheme {
        MainContainer()
    }
}