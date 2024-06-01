package com.example.promath.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.promath.ui.themenew.palette

@Composable
fun RatingList() {
    val listRating = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        listRating.forEach {
            item {
                RatingListItem()
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
@Preview
fun PreviewRatingList() {
    RatingList()
}