package com.example.promath.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.promath.ui.theme.ProMathTheme

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick
        },
        modifier = Modifier
    ) {
        Text(text = text)
    }
}

@Composable
@Preview
fun SecondaryButtonPreview() {
    ProMathTheme {
        Surface(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {
            SecondaryButton(
                text = "Test"
            ) {

            }
        }
    }
}