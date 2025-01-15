package com.example.testcharts.ui.composecharts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcharts.ui.core.ChartCard
import com.example.testcharts.ui.theme.Colors
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

private const val SELECTED_SCALE = 1.1f
private const val SELECTED_PADDING = 0f

@Composable
fun PieChartCard(
    modifier: Modifier = Modifier,
) {
    var data by remember {
        mutableStateOf(
            listOf(
                Pie(
                    label = "A",
                    data = 12.0,
                    color = Colors.yellow_500,
                    selectedColor = Colors.yellow_500,
                ),
                Pie(
                    label = "B",
                    data = 24.0,
                    color = Colors.cyan_500,
                    selectedColor = Colors.cyan_500,
                ),
                Pie(
                    label = "D",
                    data = 8.0,
                    color = Colors.magenta_500,
                    selectedColor = Colors.magenta_500,
                ),
            )
        )
    }

    ChartCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            PieChart(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                data = data,
                onPieClick = { pieData ->
                    val pieIndex = data.indexOf(pieData)
                    data = data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
                },
                selectedScale = SELECTED_SCALE,
                selectedPaddingDegree = SELECTED_PADDING,
                spaceDegree = 5f,
                style = Pie.Style.Fill,
            )
            PieChart(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                data = data,
                onPieClick = { pieData ->
                    val pieIndex = data.indexOf(pieData)
                    data = data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }

                },
                selectedScale = SELECTED_SCALE,
                selectedPaddingDegree = SELECTED_PADDING,
                spaceDegree = 3f,
                style = Pie.Style.Stroke(width = 24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PieChartCardPreview() {
    PieChartCard()
}