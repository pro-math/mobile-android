package com.example.promath.ui.screen

import com.example.promath.ui.themenew.palette
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.promath.ui.components.FieldDecision

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .background(color = palette.base100)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FieldDecision()
    }
}

@Composable
@Preview
fun PreviewMainScreen() {
    MainScreen()
}