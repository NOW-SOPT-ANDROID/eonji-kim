package com.sopt.now.compose.core_ui.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.core_ui.theme.NOWSOPTAndroidTheme

@Composable
fun TextFieldWithTitle(
    text: String = "text_test",
    value: String = "value_test",
    onValueChange: (String) -> Unit = {},
    label: String = "label_test",
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithTitlePreview() {
    NOWSOPTAndroidTheme {
        Column {
            TextFieldWithTitle()
        }
    }
}