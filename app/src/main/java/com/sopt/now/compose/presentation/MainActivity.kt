package com.sopt.now.compose.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.sopt.now.compose.presentation.login.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, LoginActivity::class.java))
    }
}