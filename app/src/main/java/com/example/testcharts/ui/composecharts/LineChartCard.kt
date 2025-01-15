package com.example.testcharts.ui.composecharts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcharts.ui.core.ChartCard
import com.example.testcharts.ui.theme.Colors
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Line
import kotlin.random.Random

private const val DATA_SIZE = 12
private const val DOT_RADIUS_DP = 2
private const val DOT_STROKE_WIDTH_DP = 1

@Composable
fun LineChartCard(
    modifier: Modifier = Modifier,
) {
    val data = remember {
        listOf(
            Line(
                label = "A",
                values = List(DATA_SIZE) {
                    val linearValue = ((it + 1) * 100.0)
                    linearValue * Random.nextDouble(from = 0.5, until = 1.0)
                },
                color = SolidColor(Colors.green_500),
                firstGradientFillColor = Colors.green_700,
                secondGradientFillColor = Color.Transparent,
                curvedEdges = false,
                dotProperties = DotProperties(
                    enabled = true,
                    radius = DOT_RADIUS_DP.dp,
                    strokeWidth = DOT_STROKE_WIDTH_DP.dp,
                    color = SolidColor(Colors.green_500),
                    strokeColor = SolidColor(Color.White),

                    )
            ),
            Line(
                label = "B",
                values = List(DATA_SIZE) {
                    val linearValue = ((DATA_SIZE - it) * 100.0)
                    linearValue * Random.nextDouble(from = 0.5, until = 1.0)
                },
                color = SolidColor(Colors.red_500),
                firstGradientFillColor = Colors.red_700,
                secondGradientFillColor = Color.Transparent,
                dotProperties = DotProperties(
                    enabled = true,
                    radius = DOT_RADIUS_DP.dp,
                    strokeWidth = DOT_STROKE_WIDTH_DP.dp,
                    color = SolidColor(Colors.red_500),
                    strokeColor = SolidColor(Color.White),
                ),
            )
        )
    }
    ChartCard(modifier = modifier) {
        LineChart(
            data = data,
            labelHelperProperties = LabelHelperProperties(enabled = false)
        )
    }
}

@Preview
@Composable
private fun LineChartCardPreview() {
    LineChartCard()
}