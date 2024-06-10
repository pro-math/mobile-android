package com.example.promath.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promath.ui.components.FullInfoGameDialog
import com.example.promath.ui.components.FullInfoGameHistoryDialog
import com.example.promath.ui.components.ModeSelection
import com.example.promath.ui.components.ModeSelectionUserRating
import com.example.promath.ui.components.RatingList
import com.example.promath.ui.components.UserRatingList
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.UserRatingViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserRatingScreen(vm: UserRatingViewModel) {
    val currentOperations by vm.currentOperation.observeAsState()
    val currentType by vm.currentType.observeAsState()
    val currentCount by vm.currentCount.observeAsState()
    val currentDifficulty by vm.currentDifficulty.observeAsState()
    val currentTime by vm.currentTime.observeAsState()

    val isOpenDialog by vm.isOpenDialog.observeAsState()
    val openDialogElement by vm.openDialogElement.observeAsState()

    if (isOpenDialog == true) {
        FullInfoGameHistoryDialog(element = openDialogElement!!) {
            vm.isOpenDialog.postValue(false)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base100)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "История ваших игр", color = palette.baseContent, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        ModeSelectionUserRating(vm = vm)
        Spacer(modifier = Modifier.height(8.dp))
        UserRatingList(vm = vm)
    }

    LaunchedEffect(
        currentOperations
    ) {
        vm.loadRating()
    }
    LaunchedEffect(
        currentCount
    ) {
        vm.loadRating()
    }
    LaunchedEffect(
        currentDifficulty
    ) {
        vm.loadRating()
    }
    LaunchedEffect(
        currentType
    ) {
        vm.loadRating()
    }
    LaunchedEffect(
        currentTime
    ) {
        vm.loadRating()
    }
}

@Composable
@Preview
fun PreviewUserRatingScreen() {
//    UserRatingScreen()
}