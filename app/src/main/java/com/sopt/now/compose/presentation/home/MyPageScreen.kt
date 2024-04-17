package com.sopt.now.compose.presentation.home

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.component.text.TextWithTitle
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.Grey700
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun MyPageScreen(user: User) {
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
                text = user.nickname,
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
                text = user.age,
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
        TextWithTitle("ID", user.id, 40.dp)
        // Pw
        TextWithTitle("비밀번호", user.pw)
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    NOWSOPTAndroidTheme {
        MyPageScreen(User("id_test", "pw_test", "nickname_test", "age_test"))
    }
}