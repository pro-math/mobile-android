package com.example.promath.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.promath.ui.themenew.palette

@Composable
fun CustomDropdownMenu(
    list: List<String>, // Menu Options
    defaultSelected: String, // Default Selected Option on load
    color: Color, // Color
    modifier: Modifier, //
    defaultSelect: Int, // Pass the Selected Option
    onSelected: (Int) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(defaultSelect) }
    var expand by remember { mutableStateOf(false) }
    var stroke by remember { mutableStateOf(2) }
//    var selected by remember {
//        mutableStateOf(li)
//    }
    Box(
        modifier
            .padding(8.dp)
            .border(
                border = BorderStroke(stroke.dp, color),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                expand = true
                stroke = if (expand) 2 else 2
            },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Тема",
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        DropdownMenu(
            expanded = expand,
            onDismissRequest = {
                expand = false
                stroke = if (expand) 2 else 2
            },
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
            modifier = Modifier
                .background(palette.base200)
                .padding(2.dp)
                .fillMaxWidth(.4f)
        ) {
            list.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expand = false
                        stroke = if (expand) 2 else 2
                        onSelected(selectedIndex)
                    },
                    text = {
                        Text(
                            text = item,
                            color = color,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }
        }

    }
}