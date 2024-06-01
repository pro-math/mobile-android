package com.example.promath.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.promath.ui.themenew.palette

@Composable
fun RatingScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base100)
    ) {

    }

}

@Composable
@Preview
fun PreviewRatingScreen() {
    RatingScreen()
}