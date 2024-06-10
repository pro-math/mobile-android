package com.example.promath.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.GameMode
import com.example.domain.models.RatingElementModel
import com.example.domain.models.UserHistoryElementModel
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.RatingViewModel
import com.example.promath.viewmodel.UserRatingViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RatingListItem(ratingElement: RatingElementModel, position: Int, vm: RatingViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base300, shape = RoundedCornerShape(10.dp))
            .clickable {
                vm.openDialogElement.postValue(ratingElement)
                vm.isOpenDialog.postValue(true)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = position.toString(),
            color = palette.primary,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = palette.baseContent
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = ratingElement.username,
            color = palette.baseContent,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1F)
        )
        if (ratingElement.gameMode == GameMode.CountMode) {
            Text(
                text = (ratingElement.correctCount * 100 / ratingElement.totalCount).toString() + "%",
                color = palette.accent,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Spacer(modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(color = palette.baseContent, shape = RoundedCornerShape(1.dp)))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${ratingElement.duration} с",
                color = palette.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        } else {
            Text(
                text = ratingElement.correctCount.toString(),
                color = palette.accent,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Spacer(modifier = Modifier
//                .width(1.dp)
//                .fillMaxHeight()
//                .background(color = palette.baseContent, shape = RoundedCornerShape(1.dp)))
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(
//                text = "${ratingElement.duration} s",
//                color = palette.secondary,
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp
//            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserRatingListItem(ratingElement: UserHistoryElementModel, position: Int, vm: UserRatingViewModel) {
    val accessor = DateTimeFormatter.ISO_DATE_TIME.parse(ratingElement.createdAt)
    val date = LocalDateTime.from(accessor)
    val dateString = "${date.year}-${date.monthValue}-${date.dayOfMonth}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base300, shape = RoundedCornerShape(10.dp))
            .clickable {
                vm.openDialogElement.postValue(ratingElement)
                vm.isOpenDialog.postValue(true)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = position.toString(),
            color = palette.primary,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = palette.baseContent
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = dateString,
            color = palette.baseContent,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1F)
        )
        if (ratingElement.gameMode == GameMode.CountMode) {
            Text(
                text = (ratingElement.correctCount * 100 / ratingElement.totalCount).toString() + "%",
                color = palette.accent,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Spacer(modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(color = palette.baseContent, shape = RoundedCornerShape(1.dp)))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${ratingElement.duration} с",
                color = palette.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        } else {
            Text(
                text = ratingElement.correctCount.toString(),
                color = palette.accent,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
@Preview
fun PreviewRatingListItem() {
//    RatingListItem()
}