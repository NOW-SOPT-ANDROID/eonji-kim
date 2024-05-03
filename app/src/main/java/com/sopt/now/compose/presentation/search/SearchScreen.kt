package com.sopt.now.compose.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("search")
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    NOWSOPTAndroidTheme {
        SearchScreen()
    }
}