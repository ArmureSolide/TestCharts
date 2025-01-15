package com.example.testcharts.ui.vico

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testcharts.ui.core.ChartCard
import com.example.testcharts.ui.theme.Colors
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer

private const val DATA_SIZE = 12
private const val COLUMN_WIDTH_DP = 16f

private val barColors = listOf(
    Colors.cyan_500,
    Colors.magenta_500,
)
private val lineColor = Colors.yellow_500

@Composable
fun VicoCombinedChartCard(modifier: Modifier = Modifier) {
    val data = remember {
        CartesianChartModel(
            ColumnCartesianLayerModel.build {
                series(*Array(DATA_SIZE) { (0..20).random() })
                series(*Array(DATA_SIZE) { (0..10).random() })
            },
            LineCartesianLayerModel.build {
                series(*Array(DATA_SIZE) { (5..15).random() })
            },
        )
    }
    ChartCard(modifier = modifier.wrapContentHeight()) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberColumnCartesianLayer(
                    columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                        barColors.map { color ->
                            rememberLineComponent(
                                fill = fill(color),
                                thickness = COLUMN_WIDTH_DP.dp,
                            )
                        }
                    ),
                ),
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(
                            LineCartesianLayer.LineFill.single(
                                fill(lineColor)
                            ),
                        ),
                    ),
                ),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(),
            ),
            model = data,
        )
    }
}