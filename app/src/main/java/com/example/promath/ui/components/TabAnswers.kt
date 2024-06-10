package com.example.promath.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.MainViewModel

@Composable
fun TabAnswers(vm :MainViewModel) {
    val examples by vm.countExamples.observeAsState()
    val success by vm.countSuccessAnswer.observeAsState()
    val mistakes = examples!! - success!!
    val percent = if (examples != 0) {
        success!! * 100 / examples!!
    } else {
        0
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(size = 10.dp))
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
//                .weight(1F)
        ) {
            Text(
                text = "Процент: ",
                color = palette.baseContent,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Text(
                text = "$percent%",
                color = palette.info,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
//                .weight(1F)
        ) {
            Text(
                text = "Правильно: ",
                color = palette.baseContent,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Text(
                text = success.toString(),
                color = palette.success,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
//                .weight(1F)
        ) {
            Text(
                text = "Не правильно: ",
                color = palette.baseContent,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Text(
                text = mistakes.toString(),
                color = palette.error,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun TabAnswersItem(
    headerText: String,
    valueText: String,
    valueColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = headerText,
            color = palette.baseContent,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Text(
            text = valueText,
            color = valueColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Composable
@Preview
fun PreviewTabAnswers() {
//    TabAnswers()
}