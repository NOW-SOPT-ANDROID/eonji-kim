package com.sopt.now.compose.core_ui.component.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.sopt.now.compose.R
import com.sopt.now.compose.core_ui.theme.GreenMain
import com.sopt.now.compose.core_ui.theme.NOWSOPTAndroidTheme

@Composable
fun TextWithRow(
    painter: Any,
    contentDescription: String,
    profileSize: Dp,
    name: String,
    nameSize: TextUnit,
    description: String,
    descriptionSize: TextUnit,
    descriptionColor: Color,
    rowVertical: Dp = 0.dp,
    spacerWidth: Dp = 0.dp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = rowVertical),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (painter is Int) {
            Image(
                painter = painterResource(id = painter),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(profileSize)
                    .aspectRatio(1f / 1f),
            )
        } else if (painter is String) {
            Image(
                painter = rememberImagePainter(data = painter),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(profileSize)
                    .aspectRatio(1f / 1f),
            )
        }
        Spacer(modifier = Modifier.width(spacerWidth))
        Text(
            text = name,
            fontSize = nameSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(spacerWidth))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = description,
            fontSize = descriptionSize,
            modifier = Modifier
                .border(
                    width = 1.5.dp,
                    color = descriptionColor,
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextWithRowPreview() {
    NOWSOPTAndroidTheme {
        Column {
            TextWithRow(
                R.drawable.img_user_profile_dororo,
                "contentDescription_test",
                60.dp,
                "name_test",
                16.sp,
                "description_test",
                13.sp,
                GreenMain
            )
        }
    }
}