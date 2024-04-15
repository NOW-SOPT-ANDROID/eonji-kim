package com.sopt.now.presentation.home

import androidx.lifecycle.ViewModel
import com.sopt.now.R
import com.sopt.now.presentation.model.Friend

class HomeViewModel : ViewModel() {
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
            profileImage = R.drawable.img_friend_kururu,
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
            profileImage = R.drawable.img_friend_kururu,
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
            profileImage = R.drawable.img_friend_kururu,
            name = "쿠루루",
            selfDescription = "쿠루",
        ),
    )
}