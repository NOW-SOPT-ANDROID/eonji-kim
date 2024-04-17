package com.sopt.now.compose.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.component.text.TextWithRow
import com.sopt.now.compose.model.Friend
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.YellowMain

@Composable
fun HomeScreen(user: User) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // User
        item {
            TextWithRow(
                R.drawable.img_user_profile_dororo,
                "user profile img",
                75.dp,
                user.nickname,
                18.sp,
                "나 ${user.age}세 응애",
                14.sp,
                YellowMain,
                15.dp,
                12.dp,
            )
        }

        // Friend
        items(mockFriendList) { friend ->
            TextWithRow(
                friend.profileImage,
                "friend profile img",
                50.dp,
                friend.name,
                16.sp,
                friend.selfDescription,
                13.sp,
                GreenMain,
                8.dp,
                10.dp,
            )
        }
    }
}

val mockFriendList = listOf(
    Friend(
        profileImage = R.drawable.img_friend_profile_keroro,
        name = "케로로",
        selfDescription = "케로케로",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_kiroro,
        name = "기로로",
        selfDescription = "기로기로기로",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_tamama,
        name = "타마마",
        selfDescription = "타마타마타마타마",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_kururu,
        name = "쿠루루",
        selfDescription = "쿠루",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_keroro,
        name = "케로로",
        selfDescription = "케로케로",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_kiroro,
        name = "기로로",
        selfDescription = "기로기로기로",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_tamama,
        name = "타마마",
        selfDescription = "타마타마타마타마",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_kururu,
        name = "쿠루루",
        selfDescription = "쿠루",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_keroro,
        name = "케로로",
        selfDescription = "케로케로",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_kiroro,
        name = "기로로",
        selfDescription = "기로기로기로",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_tamama,
        name = "타마마",
        selfDescription = "타마타마타마타마",
    ),
    Friend(
        profileImage = R.drawable.img_friend_profile_kururu,
        name = "쿠루루",
        selfDescription = "쿠루",
    ),
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NOWSOPTAndroidTheme {
        HomeScreen(User("id_test", "pw_test", "nickname_test", "age_test"))
    }
}