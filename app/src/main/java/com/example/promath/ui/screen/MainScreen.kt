package com.example.promath.ui.screen

import com.example.promath.ui.themenew.palette
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.promath.ui.components.FieldDecision
import com.example.promath.ui.components.TabAnswers
import com.example.promath.viewmodel.MainViewModel

@Composable
fun MainScreen(vm: MainViewModel) {
    vm.loadExample()

    Column(
        modifier = Modifier
            .background(color = palette.base100)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FieldDecision(vm = vm)
        Spacer(modifier = Modifier.height(8.dp))
        TabAnswers()
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.primary,
                contentColor = palette.primaryContent
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Start"
            )
        }
    }
}