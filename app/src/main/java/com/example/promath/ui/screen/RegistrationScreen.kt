package com.example.promath.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.promath.ui.components.RegistrationCard
import com.example.promath.ui.themenew.palette

@Composable
fun RegistrationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base100)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        RegistrationCard()
        Spacer(modifier = Modifier.height(8.dp))
    }
}