package com.example.promath.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.RatingViewModel
import com.example.promath.viewmodel.UserRatingViewModel

@Composable
fun RatingList(vm: RatingViewModel) {
    val listRating by vm.listRating.observeAsState()
    val state = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        state = state
    ) {
        for (i in listRating!!.indices) {
            item {
                RatingListItem(listRating!![i], i + 1, vm = vm)
                Spacer(modifier = Modifier.height(4.dp))
            }
//            if (i == listRating!!.size - 1) {
//                vm.offset += 5
//                vm.loadRating()
//            }
        }
    }

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == listRating!!.size - 1 && !vm.isLastGet) {
                    vm.offset += 50
                    vm.loadRating(typeAdd = true)
                }
            }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserRatingList(vm: UserRatingViewModel) {
    val listRating by vm.listRating.observeAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        for (i in listRating!!.indices) {
            item {
                UserRatingListItem(listRating!![i], i + 1, vm = vm)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
@Preview
fun PreviewRatingList() {
//    RatingList()
}