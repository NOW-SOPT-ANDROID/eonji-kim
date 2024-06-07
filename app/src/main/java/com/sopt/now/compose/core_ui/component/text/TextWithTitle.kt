package com.sopt.now.compose.core_ui.component.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.core_ui.theme.Grey600
import com.sopt.now.compose.core_ui.theme.NOWSOPTAndroidTheme

@Composable
fun TextWithTitle(
    title: String = "title_test",
    text: String = "text_test",
    bottom: Dp = 0.dp
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp
        )
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = bottom),
            color = Grey600
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextWithTitlePreview() {
    NOWSOPTAndroidTheme {
        Column {
            TextWithTitle()
        }
    }
}