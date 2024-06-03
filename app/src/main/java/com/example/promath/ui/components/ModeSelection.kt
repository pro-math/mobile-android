package com.example.promath.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.MainViewModel

@Composable
fun ModeSelection(vm: MainViewModel) {

    val currentDifficulty by vm.currentDifficulty.observeAsState()
    val currentOperation by vm.currentOperation.observeAsState()
    val currentType by vm.currentType.observeAsState()
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
            SelectionTab(selectionName = "Difficulty", elements = listOf("10", "100", "1000"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTab(selectionName = "Operations", elements = listOf("+", "-", "*", "/"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTab(selectionName = "Type", elements = listOf("Time", "Count"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            if (currentType == 0) {
                SelectionTab(selectionName = "Time", elements = listOf("30s", "60s", "90s"), vm = vm)
            } else {
                SelectionTab(selectionName = "Count", elements = listOf("10", "100"), vm = vm)
            }
        }
    }
}

@Composable
private fun SelectionTab(selectionName: String, elements: List<String>, vm: MainViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedItem by when (selectionName) {
            "Difficulty" -> vm.currentDifficulty.observeAsState()
            "Operations" -> vm.currentOperation.observeAsState()
            "Type" -> vm.currentType.observeAsState()
            "Time" -> vm.currentTime.observeAsState()
            "Count" -> vm.currentCount.observeAsState()
            else -> vm.currentDifficulty.observeAsState()
        }

        Text(
            text = selectionName,
            color = palette.baseContent,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(8.dp))
        LazyRow(
            modifier = Modifier
                .background(color = palette.base300, shape = RoundedCornerShape(10.dp))
        ) {
            for(i in elements.indices) {
                val containerColor = if (i == selectedItem) {
                    palette.primary
                } else {
                    palette.base300
                }
                val contentColor = if (i == selectedItem) {
                    palette.primaryContent
                } else {
                    palette.baseContent
                }
                item {
                    Button(
                        onClick = {
                            when (selectionName) {
                                "Difficulty" -> vm.currentDifficulty.postValue(i)
                                "Operations" -> vm.currentOperation.postValue(i)
                                "Type" -> vm.currentType.postValue(i)
                                "Time" -> vm.currentTime.postValue(i)
                                "Count" -> vm.currentCount.postValue(i)
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = contentColor
                        )
                    ) {
                        Text(
                            text = elements[i]
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewModeSelection() {
//    ModeSelection()
}