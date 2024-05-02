package com.sopt.now.compose.presentation.sign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class SignActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignScreen()
                }
            }
        }
    }

    companion object {
        const val MIN_ID_LEN = 6
        const val MAX_ID_LEN = 10
        const val MIN_PW_LEN = 8
        const val MAX_PW_LEN = 12
        const val MIN_AGE_LEN = 1
        const val MAX_AGE_LEN = 3
    }
}