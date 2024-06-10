package com.example.promath.ui.components

import android.util.Log
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
import com.example.promath.viewmodel.ProgressViewModel
import com.example.promath.viewmodel.RatingViewModel
import com.example.promath.viewmodel.UserRatingViewModel

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
                text = "Выбор режима",
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
            SelectionTab(selectionName = "Сложность", elements = listOf("10", "100", "1000"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTab(selectionName = "Операции", elements = listOf("+", "-", "*", "/"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTab(selectionName = "Тип", elements = listOf("На время", "На количество"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            if (currentType == 0) {
                SelectionTab(selectionName = "Время", elements = listOf("15 с", "30 с", "60 с", "90 с"), vm = vm)
            } else {
                SelectionTab(selectionName = "Количество", elements = listOf("10", "15", "20", "30"), vm = vm)
            }
        }
    }
}

@Composable
fun ModeSelectionRating(vm: RatingViewModel) {

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
                text = "Выбор режима",
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
            SelectionTabRating(selectionName = "Сложность", elements = listOf("10", "100", "1000"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTabRating(selectionName = "Операции", elements = listOf("+", "-", "*", "/"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTabRating(selectionName = "Тип", elements = listOf("На время", "На количество"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            if (currentType == 0) {
                SelectionTabRating(selectionName = "Время", elements = listOf("15 с", "30 с", "60 с", "90 с"), vm = vm)
            } else {
                SelectionTabRating(selectionName = "Количество", elements = listOf("10", "15", "20", "30"), vm = vm)
            }
        }
    }
}

@Composable
private fun SelectionTabRating(selectionName: String, elements: List<String>, vm: RatingViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedItem by when (selectionName) {
            "Сложность" -> vm.currentDifficulty.observeAsState()
            "Операции" -> vm.currentOperation.observeAsState()
            "Тип" -> vm.currentType.observeAsState()
            "Время" -> vm.currentTime.observeAsState()
            "Количество" -> vm.currentCount.observeAsState()
            else -> vm.currentDifficulty.observeAsState()
        }
        val selectedOperations by vm.currentOperation.observeAsState()

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
                var containerColor = if (i == selectedItem) {
                    palette.primary
                } else {
                    palette.base300
                }
                var contentColor = if (i == selectedItem) {
                    palette.primaryContent
                } else {
                    palette.baseContent
                }
                if (selectionName == "Операции") {
                    containerColor = if (selectedOperations!![i]) {
                        palette.primary
                    } else {
                        palette.base300
                    }
                    contentColor = if (selectedOperations!![i]) {
                        palette.primaryContent
                    } else {
                        palette.baseContent
                    }
                }
                item {
                    Button(
                        onClick = {
                            when (selectionName) {
                                "Сложность" -> vm.currentDifficulty.postValue(i)
                                "Операции" -> {
                                    var operations: MutableList<Boolean> = mutableListOf(false, false, false, false)
                                    for (ind in selectedOperations!!.indices) {
                                        if (ind == i) {
                                            operations[ind] = !selectedOperations!![ind]
                                        } else {
                                            operations[ind] = selectedOperations!![ind]
                                        }
                                    }
                                    if (operations == mutableListOf(false, false, false, false)) {
                                        operations = mutableListOf(true, true, true, true)
                                    }
                                    Log.i("TEST_OPERATIONS", operations.toString())
                                    vm.currentOperation.postValue(operations)
                                }
                                "Тип" -> vm.currentType.postValue(i)
                                "Время" -> vm.currentTime.postValue(i)
                                "Количество" -> vm.currentCount.postValue(i)
                            }
//                            vm.loadRating()
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
private fun SelectionTab(selectionName: String, elements: List<String>, vm: MainViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedItem by when (selectionName) {
            "Сложность" -> vm.currentDifficulty.observeAsState()
            "Операции" -> vm.currentOperation.observeAsState()
            "Тип" -> vm.currentType.observeAsState()
            "Время" -> vm.currentTime.observeAsState()
            "Количество" -> vm.currentCount.observeAsState()
            else -> vm.currentDifficulty.observeAsState()
        }
        val selectedOperations by vm.currentOperation.observeAsState()

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
                var containerColor = if (i == selectedItem) {
                    palette.primary
                } else {
                    palette.base300
                }
                var contentColor = if (i == selectedItem) {
                    palette.primaryContent
                } else {
                    palette.baseContent
                }
                if (selectionName == "Операции") {
                    containerColor = if (selectedOperations!![i]) {
                        palette.primary
                    } else {
                        palette.base300
                    }
                    contentColor = if (selectedOperations!![i]) {
                        palette.primaryContent
                    } else {
                        palette.baseContent
                    }
                }
                item {
                    Button(
                        onClick = {
                            when (selectionName) {
                                "Сложность" -> vm.currentDifficulty.postValue(i)
                                "Операции" -> {
                                    var operations: MutableList<Boolean> = mutableListOf(false, false, false, false)
                                    for (ind in selectedOperations!!.indices) {
                                        if (ind == i) {
                                            operations[ind] = !selectedOperations!![ind]
                                        } else {
                                            operations[ind] = selectedOperations!![ind]
                                        }
                                    }
                                    if (operations == mutableListOf(false, false, false, false)) {
                                        operations = mutableListOf(true, true, true, true)
                                    }
                                    Log.i("TEST_OPERATIONS", operations.toString())
                                    vm.currentOperation.postValue(operations)
                                }
                                "Тип" -> vm.currentType.postValue(i)
                                "Время" -> vm.currentTime.postValue(i)
                                "Количество" -> vm.currentCount.postValue(i)
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

@Composable
fun ModeSelectionProgress(vm: ProgressViewModel) {

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
                text = "Настройка режима",
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
            SelectionTabProgress(selectionName = "Сложность", elements = listOf("10", "100", "1000"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTabProgress(selectionName = "Операции", elements = listOf("+", "-", "*", "/"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTabProgress(selectionName = "Тип", elements = listOf("На время", "На количество"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            if (currentType == 0) {
                SelectionTabProgress(selectionName = "Время", elements = listOf("15 с", "30 с", "60 с", "90 с"), vm = vm)
            } else {
                SelectionTabProgress(selectionName = "Количество", elements = listOf("10", "15", "20", "30"), vm = vm)
            }
        }
    }
}

@Composable
private fun SelectionTabProgress(selectionName: String, elements: List<String>, vm: ProgressViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedItem by when (selectionName) {
            "Сложность" -> vm.currentDifficulty.observeAsState()
            "Операции" -> vm.currentOperation.observeAsState()
            "Тип" -> vm.currentType.observeAsState()
            "Время" -> vm.currentTime.observeAsState()
            "Количество" -> vm.currentCount.observeAsState()
            else -> vm.currentDifficulty.observeAsState()
        }
        val selectedOperations by vm.currentOperation.observeAsState()

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
                var containerColor = if (i == selectedItem) {
                    palette.primary
                } else {
                    palette.base300
                }
                var contentColor = if (i == selectedItem) {
                    palette.primaryContent
                } else {
                    palette.baseContent
                }
                if (selectionName == "Операции") {
                    containerColor = if (selectedOperations!![i]) {
                        palette.primary
                    } else {
                        palette.base300
                    }
                    contentColor = if (selectedOperations!![i]) {
                        palette.primaryContent
                    } else {
                        palette.baseContent
                    }
                }
                item {
                    Button(
                        onClick = {
                            when (selectionName) {
                                "Сложность" -> vm.currentDifficulty.postValue(i)
                                "Операции" -> {
                                    var operations: MutableList<Boolean> = mutableListOf(false, false, false, false)
                                    for (ind in selectedOperations!!.indices) {
                                        if (ind == i) {
                                            operations[ind] = !selectedOperations!![ind]
                                        } else {
                                            operations[ind] = selectedOperations!![ind]
                                        }
                                    }
                                    if (operations == mutableListOf(false, false, false, false)) {
                                        operations = mutableListOf(true, true, true, true)
                                    }
                                    Log.i("TEST_OPERATIONS", operations.toString())
                                    vm.currentOperation.postValue(operations)
                                }
                                "Тип" -> vm.currentType.postValue(i)
                                "Время" -> vm.currentTime.postValue(i)
                                "Количество" -> vm.currentCount.postValue(i)
                            }
//                            vm.loadRating()
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
fun ModeSelectionUserRating(vm: UserRatingViewModel) {

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
                text = "Настройка режима",
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
            SelectionTabUserRating(selectionName = "Сложность", elements = listOf("10", "100", "1000"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTabUserRating(selectionName = "Операции", elements = listOf("+", "-", "*", "/"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            SelectionTabUserRating(selectionName = "Тип", elements = listOf("На время", "На количество"), vm = vm)
            Spacer(modifier = Modifier.height(8.dp))
            if (currentType == 0) {
                SelectionTabUserRating(selectionName = "Время", elements = listOf("15 с", "30 с", "60 с", "90 с"), vm = vm)
            } else {
                SelectionTabUserRating(selectionName = "Количество", elements = listOf("10", "15", "20", "30"), vm = vm)
            }
        }
    }
}

@Composable
private fun SelectionTabUserRating(selectionName: String, elements: List<String>, vm: UserRatingViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedItem by when (selectionName) {
            "Сложность" -> vm.currentDifficulty.observeAsState()
            "Операции" -> vm.currentOperation.observeAsState()
            "Тип" -> vm.currentType.observeAsState()
            "Время" -> vm.currentTime.observeAsState()
            "Количество" -> vm.currentCount.observeAsState()
            else -> vm.currentDifficulty.observeAsState()
        }
        val selectedOperations by vm.currentOperation.observeAsState()

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
                var containerColor = if (i == selectedItem) {
                    palette.primary
                } else {
                    palette.base300
                }
                var contentColor = if (i == selectedItem) {
                    palette.primaryContent
                } else {
                    palette.baseContent
                }
                if (selectionName == "Операции") {
                    containerColor = if (selectedOperations!![i]) {
                        palette.primary
                    } else {
                        palette.base300
                    }
                    contentColor = if (selectedOperations!![i]) {
                        palette.primaryContent
                    } else {
                        palette.baseContent
                    }
                }
                item {
                    Button(
                        onClick = {
                            when (selectionName) {
                                "Сложность" -> vm.currentDifficulty.postValue(i)
                                "Операции" -> {
                                    var operations: MutableList<Boolean> = mutableListOf(false, false, false, false)
                                    for (ind in selectedOperations!!.indices) {
                                        if (ind == i) {
                                            operations[ind] = !selectedOperations!![ind]
                                        } else {
                                            operations[ind] = selectedOperations!![ind]
                                        }
                                    }
                                    if (operations == mutableListOf(false, false, false, false)) {
                                        operations = mutableListOf(true, true, true, true)
                                    }
                                    Log.i("TEST_OPERATIONS", operations.toString())
                                    vm.currentOperation.postValue(operations)
                                }
                                "Тип" -> vm.currentType.postValue(i)
                                "Время" -> vm.currentTime.postValue(i)
                                "Количество" -> vm.currentCount.postValue(i)
                            }
//                            vm.loadRating()
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