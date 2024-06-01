package com.example.promath.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.promath.ui.components.RatingList
import com.example.promath.ui.components.RatingTab
import com.example.promath.ui.themenew.palette

@Composable
fun RatingScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base100)
            .padding(16.dp)
    ) {
        RatingTab()
        Spacer(modifier = Modifier.height(16.dp))
        RatingList()
    }

}

@Composable
@Preview
fun PreviewRatingScreen() {
    RatingScreen()
}