package com.example.promath.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.promath.ui.themenew.palette

@Composable
fun ModeSelection() {

    var isOpen by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .clickable {
                isOpen = !isOpen
            }
            .animateContentSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Mode selection",
                color = palette.baseContent,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1F)
            )
            if (isOpen) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null, tint = palette.baseContent)
            } else {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = palette.baseContent)
            }
        }
        if (isOpen) {
            Text(text = "Много настроек разненьких", color = palette.baseContent)
        }
    }
}

@Composable
@Preview
fun PreviewModeSelection() {
    ModeSelection()
}