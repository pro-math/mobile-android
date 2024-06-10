package com.example.promath.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promath.ui.components.FullInfoGameDialog
import com.example.promath.ui.components.ModeSelectionRating
import com.example.promath.ui.components.RatingList
import com.example.promath.ui.components.RatingTab
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.RatingViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatingScreen(vm: RatingViewModel) {

    val currentOperations by vm.currentOperation.observeAsState()
    val currentType by vm.currentType.observeAsState()
    val currentCount by vm.currentCount.observeAsState()
    val currentDifficulty by vm.currentDifficulty.observeAsState()
    val currentTime by vm.currentTime.observeAsState()

    val isOpenDialog by vm.isOpenDialog.observeAsState()
    val openDialogElement by vm.openDialogElement.observeAsState()

    if (isOpenDialog == true) {
        FullInfoGameDialog(element = openDialogElement!!) {
            vm.isOpenDialog.postValue(false)
        }
    }

//    vm.loadRating()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base100)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Рейтинговая таблица", color = palette.baseContent, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, modifier = Modifier.weight(1F))
            IconButton(onClick = {
                vm.offset = 0
                vm.isLastGet = false
                vm.loadRating(false)
            }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = palette.baseContent)
            }
        }
        ModeSelectionRating(vm = vm)
        Spacer(modifier = Modifier.height(16.dp))
        RatingList(vm = vm)
    }

    LaunchedEffect(
        currentOperations
    ) {
        vm.offset = 0
        vm.isLastGet = false
        vm.loadRating(false)
    }
    LaunchedEffect(
        currentCount
    ) {
        vm.offset = 0
        vm.isLastGet = false
        vm.loadRating(false)
    }
    LaunchedEffect(
        currentDifficulty
    ) {
        vm.offset = 0
        vm.isLastGet = false
        vm.loadRating(false)
    }
    LaunchedEffect(
        currentType
    ) {
        vm.offset = 0
        vm.isLastGet = false
        vm.loadRating(false)
    }
    LaunchedEffect(
        currentTime
    ) {
        vm.offset = 0
        vm.isLastGet = false
        vm.loadRating(false)
    }

}

@Composable
@Preview
fun PreviewRatingScreen() {
//    RatingScreen()
}