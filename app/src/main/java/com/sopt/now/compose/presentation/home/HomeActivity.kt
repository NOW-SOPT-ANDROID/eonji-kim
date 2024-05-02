package com.sopt.now.compose.presentation.home

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.model.BottomNavigationItem
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.theme.GreenMain
import com.sopt.now.compose.ui.theme.Grey300
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.YellowMain
import com.sopt.now.compose.util.KeyStorage

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent.getParcelableExtra(KeyStorage.USER_DATA) ?: User("", "", "", "")

        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeContainer(user)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContainer(user: User) {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavigationItem(
            icon = Icons.Filled.Home,
            label = "Home"
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Search,
            label = "Search"
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Person,
            label = "MyPage"
        )
    )

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 2000) {
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, context.getString(R.string.toast_home_back), Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GreenMain,
                    titleContentColor = YellowMain
                ), title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium,
                    )
                })
        },
        bottomBar = {
            NavigationBar(
                containerColor = GreenMain,
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemColors(
                            selectedIconColor = YellowMain,
                            selectedTextColor = YellowMain,
                            selectedIndicatorColor = GreenMain,
                            unselectedIconColor = Grey300,
                            unselectedTextColor = Grey300,
                            disabledIconColor = Grey300,
                            disabledTextColor = Grey300,
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedItem) {
                0 -> HomeScreen(user)
                1 -> SearchScreen()
                2 -> MyPageScreen(user)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    NOWSOPTAndroidTheme {
        HomeContainer(User("id_test", "pw_test", "nickname_test", "age_test"))
    }
}