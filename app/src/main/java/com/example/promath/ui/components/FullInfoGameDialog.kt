package com.example.promath.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.domain.models.GameMode
import com.example.domain.models.RatingElementModel
import com.example.domain.models.UserHistoryElementModel
import com.example.promath.ui.themenew.palette
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FullInfoGameDialog(
    element: RatingElementModel,
    onDismissRequest: () -> Unit
) {
    val accessor = DateTimeFormatter.ISO_DATE_TIME.parse(element.createdAt)
    val date = LocalDateTime.from(accessor)
    val dateString = "${date.year}-${date.monthValue}-${date.dayOfMonth}"

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = palette.base100
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Информация", color = palette.accent, fontWeight = FontWeight.SemiBold)
                Row {
                    Text(text = "Имя пользователя: ", color = palette.baseContent)
                    Text(text = element.username, color = palette.primary)
                }
                Row {
                    Text(text = "ID игровой сессии: ", color = palette.baseContent)
                    Text(text = element.gameSessionId.toString(), color = palette.primary)
                }
                Row {
                    Text(text = "Дата создания: ", color = palette.baseContent)
                    Text(text = dateString, color = palette.primary)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Настройки игры", color = palette.accent, fontWeight = FontWeight.SemiBold)
                Row {
                    Text(text = "Сложность: ", color = palette.baseContent)
                    Text(text = element.examplesCategory.toString(), color = palette.primary)
                }
                Row {
                    Text(text = "Операции: ", color = palette.baseContent)
                    Text(text = element.mathOperations.toString().replace("[", "").replace("]", ""), color = palette.primary)
                }
                Row {
                    Text(text = "Тип", color = palette.baseContent)
                    Text(
                        text = if (element.gameMode == GameMode.CountMode) {
                            "count mode"
                        } else {
                            "time_mode"
                        }, color = palette.primary)
                }
                Row {
                    Text(
                        text = if (element.gameMode == GameMode.CountMode) {
                            "На количество"
                        } else {
                            "На время"
                        }, color = palette.baseContent)
                    Text(
                        text = if (element.gameMode == GameMode.CountMode) {
                            element.totalCount.toString()
                        } else {
                            "${element.duration} с"
                        }, color = palette.primary)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Результаты", color = palette.accent, fontWeight = FontWeight.SemiBold)
                Row {
                    Text(text = "Решено правильно: ", color = palette.baseContent)
                    Text(text = element.correctCount.toString(), color = palette.success)
                }
                Row {
                    Text(text = "Решено не правильно: ", color = palette.baseContent)
                    Text(text = (element.totalCount - element.correctCount).toString(), color = palette.error)
                }
                Row {
                    Text(text = "Процент правильно решенных: ", color = palette.baseContent)
                    Text(text = (element.correctCount * 100 / element.totalCount).toString() + "%", color = palette.info)
                }
                Row {
                    Text(text = "Время: ", color = palette.baseContent)
                    Text(text = "30 с", color = palette.info)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(text = "Скрыть", color = palette.error)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FullInfoGameHistoryDialog(
    element: UserHistoryElementModel,
    onDismissRequest: () -> Unit
) {
    val selectedTab = remember {
        mutableIntStateOf(0)
    }

    val accessor = DateTimeFormatter.ISO_DATE_TIME.parse(element.createdAt)
    val date = LocalDateTime.from(accessor)
    val dateString = "${date.year}-${date.monthValue}-${date.dayOfMonth}"

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = palette.base100
            )
        ) {
            Column {
                TabRow(
                    selectedTabIndex = selectedTab.intValue,
                    containerColor = palette.base200,
                    contentColor = palette.baseContent,
                    indicator = @Composable { tabPositions ->
                        if (selectedTab.intValue < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.intValue]),
                                color = palette.primary
                            )
                        }
                    }
                ) {
                    Tab(
                        selected = selectedTab.intValue == 0,
                        onClick = { selectedTab.intValue = 0 },
                        text = { Text("Информация о игре") },
                        selectedContentColor = palette.primary,
                        unselectedContentColor = palette.baseContent
                    )
                    Tab(
                        selected = selectedTab.intValue == 1,
                        onClick = { selectedTab.intValue = 1 },
                        text = { Text("Примеры") },
                        selectedContentColor = palette.primary,
                        unselectedContentColor = palette.baseContent
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    if (selectedTab.intValue == 0) {
                        Text(text = "Информации", color = palette.accent, fontWeight = FontWeight.SemiBold)
                        Row {
                            Text(text = "Дата создания: ", color = palette.baseContent)
                            Text(text = dateString, color = palette.primary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Настройки игры", color = palette.accent, fontWeight = FontWeight.SemiBold)
                        Row {
                            Text(text = "Сложность: ", color = palette.baseContent)
                            Text(text = element.examplesCategory.toString(), color = palette.primary)
                        }
                        Row {
                            Text(text = "Операции: ", color = palette.baseContent)
                            Text(text = element.mathOperations.toString().replace("[", "").replace("]", ""), color = palette.primary)
                        }
                        Row {
                            Text(text = "Тип: ", color = palette.baseContent)
                            Text(
                                text = if (element.gameMode == GameMode.CountMode) {
                                    "На количество"
                                } else {
                                    "На время"
                                }, color = palette.primary)
                        }
                        Row {
                            Text(
                                text = if (element.gameMode == GameMode.CountMode) {
                                    "Количество: "
                                } else {
                                    "Время: "
                                }, color = palette.baseContent)
                            Text(
                                text = if (element.gameMode == GameMode.CountMode) {
                                    element.totalCount.toString()
                                } else {
                                    "${element.duration} с"
                                }, color = palette.primary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Results", color = palette.accent, fontWeight = FontWeight.SemiBold)
                        Row {
                            Text(text = "Решено правильно: ", color = palette.baseContent)
                            Text(text = element.correctCount.toString(), color = palette.success)
                        }
                        Row {
                            Text(text = "Решено не правильно: ", color = palette.baseContent)
                            Text(text = (element.totalCount - element.correctCount).toString(), color = palette.error)
                        }
                        Row {
                            Text(text = "Процент: ", color = palette.baseContent)
                            Text(text = (element.correctCount * 100 / element.totalCount).toString() + "%", color = palette.info)
                        }
                        Row {
                            Text(text = "Время: ", color = palette.baseContent)
                            Text(text = "30 с", color = palette.info)
                        }
                    } else {
                        LazyColumn {
                            element.examples.forEach {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = palette.base300,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "${it.number1} ${it.typeOperation} ${it.number2} = ${it.correctAnswer}",
                                            color = palette.baseContent,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "Ваш ответ: ${it.answer}",
                                            color = palette.baseContent,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = { onDismissRequest() }
                        ) {
                            Text(text = "Скрыть", color = palette.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewFullInfoGameDialog() {
//    FullInfoGameDialog()
}