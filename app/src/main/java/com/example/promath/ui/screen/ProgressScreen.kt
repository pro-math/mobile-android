package com.example.promath.ui.screen

import android.graphics.Typeface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.GameMode
import com.example.promath.ui.components.FullInfoGameDialog
import com.example.promath.ui.components.ModeSelectionProgress
import com.example.promath.ui.components.rememberMarker
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.ProgressViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.core.cartesian.FadingEdges
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.Decoration
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProgressScreen(vm: ProgressViewModel) {

    val currentOperations by vm.currentOperation.observeAsState()
    val currentType by vm.currentType.observeAsState()
    val currentCount by vm.currentCount.observeAsState()
    val currentDifficulty by vm.currentDifficulty.observeAsState()
    val currentTime by vm.currentTime.observeAsState()

    val data1 by vm.data.observeAsState()

    vm.loadData()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base100)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Прогресс", color = palette.baseContent, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        ModeSelectionProgress(vm = vm)
        Spacer(modifier = Modifier.height(8.dp))
        if (data1 != null && data1!!.isNotEmpty()) {
            val data: MutableMap<String, MutableList<Float>> = mutableMapOf(
//                "2024-06-01" to mutableListOf(10f, 40f, 30f, 20f),
//                "2024-06-02" to mutableListOf(90f),
//                "2024-06-03" to mutableListOf(100f),
            )
            data1!!.forEach {
                if (data[it.date] == null) {
                    data[it.date] = mutableListOf(it.stats)
                } else {
                    data[it.date]!!.add(it.stats)
                }
            }
            Chart(
                data = data,
                type = if (currentType == 0) {
                    GameMode.TimeMode
                } else {
                    GameMode.CountMode
                }
            )
        }
    }

    LaunchedEffect(
        currentOperations
    ) {
        vm.loadData()
    }
    LaunchedEffect(
        currentCount
    ) {
        vm.loadData()
    }
    LaunchedEffect(
        currentDifficulty
    ) {
        vm.loadData()
    }
    LaunchedEffect(
        currentType
    ) {
        vm.loadData()
    }
    LaunchedEffect(
        currentTime
    ) {
        vm.loadData()
    }
}

@Composable
@Preview
fun PreviewProgressScreen() {
//    ProgressScreen()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Chart(data: Map<String, List<Float>>, type: GameMode) {
    val xToDateMapKey = ExtraStore.Key<Map<Float, String>>()
    val xValues = mutableListOf<Float>()
    val yValues = mutableListOf<Float>()
    val xToDates = mutableMapOf<Float, String>()

    var index = 0f

    // Итерация по карте для заполнения xValues, yValues и xToDates
    data.forEach { (date, values) ->
        values.forEach { value ->
            xValues.add(index)
            yValues.add(value)
            xToDates[index] = date
            index += 1f
        }
    }

    val cartesianChartModelProducer = remember { CartesianChartModelProducer.build() }

    cartesianChartModelProducer.tryRunTransaction {
        lineSeries {
            series(xValues, yValues)
        }
        updateExtras { it[xToDateMapKey] = xToDates }
    }

    // Создание форматтера для оси X
    val formatter = CartesianValueFormatter { x, chartValues, _ ->
        chartValues.model.extraStore.getOrNull(xToDateMapKey)?.get(x)
            ?: "Неопределенная дата"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    listOf(rememberLineSpec(DynamicShader.color(palette.primary)))
                ),
                startAxis = rememberStartAxis(
                    label = rememberAxisLabelComponent(
                        color = palette.baseContent
                    ),
                    horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
                    titleComponent = rememberTextComponent(
                        color = Color.Black,
                        background = rememberShapeComponent(Shape.Pill, palette.accent),
                        padding = Dimensions.of(horizontal = 8.dp, vertical = 2.dp),
                        margins = Dimensions.of(end = 4.dp),
                        typeface = Typeface.MONOSPACE,
                        textSize = 8.sp
                    ),
                    title = if (type == GameMode.CountMode) {
                        "Процент правильно решенных %"
                    } else {
                        "Количество правильно решенных"
                    }
                ),
                bottomAxis = rememberBottomAxis(
                    valueFormatter = formatter,
                    label = rememberAxisLabelComponent(
                        color = palette.baseContent
                    ),
//                    hor = VerticalAxis.HorizontalLabelPosition.Inside,
                    titleComponent = rememberTextComponent(
                        color = Color.Black,
                        background = rememberShapeComponent(Shape.Pill, palette.accent),
                        padding = Dimensions.of(horizontal = 8.dp, vertical = 2.dp),
                        margins = Dimensions.of(end = 4.dp),
                        typeface = Typeface.MONOSPACE,
                        textSize = 8.sp
                    ),
                    title = "Дата"
                )
            ),
            modelProducer = cartesianChartModelProducer,
            marker = rememberMarker(),
            zoomState = rememberVicoZoomState(zoomEnabled = true),
            scrollState = rememberVicoScrollState(scrollEnabled = true)
        )
    }
}