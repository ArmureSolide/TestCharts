package com.example.testcharts.ui.vico

import android.text.format.DateFormat
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.testcharts.ui.core.ChartCard
import com.example.testcharts.ui.theme.Colors
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.stacked
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.toVicoShape
import com.patrykandpatrick.vico.core.cartesian.FadingEdges
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.VerticalLegend
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.Shadow
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider.Companion.verticalGradient
import java.text.DecimalFormat
import java.util.Calendar

private const val COLUMN_WIDTH_DP = 16f
private const val COLUMN_RADIUS = 4f

private val colors = listOf(
    verticalGradient(
        Colors.red_700.toArgb(),
        Colors.red_500.toArgb(),
        Colors.red_300.toArgb(),
    ),
    verticalGradient(
        Colors.green_300.toArgb(),
        Colors.green_500.toArgb(),
        Colors.green_700.toArgb(),
    ),
)

@Composable
fun VicoBarChartCard(modifier: Modifier = Modifier) {
    var data by remember {
        mutableStateOf(
            List(12) { (5..9).random() }
        )
    }

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(data) {
        modelProducer.runTransaction {
            if (data.isNotEmpty()) {
                columnSeries {
                    series(data)
                    series(data.map { -it })
                }
            }
        }
    }

    ChartCard(modifier = modifier.wrapContentHeight()) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        data = data.dropLast(1)
                    }
                ) {
                    Text("Remove column")
                }
                Button(
                    onClick = {
                        data = data + (5..9).random()
                    }
                ) {
                    Text("Add column")
                }
            }
            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                            colors.mapIndexed { index, color ->
                                rememberLineComponent(
                                    fill = fill(color),
                                    thickness = COLUMN_WIDTH_DP.dp,
                                    shape = if (index == colors.lastIndex) {
                                        AbsoluteRoundedCornerShape(
                                            bottomRight = COLUMN_RADIUS.dp,
                                            bottomLeft = COLUMN_RADIUS.dp
                                        ).toVicoShape()
                                    } else {
                                        AbsoluteRoundedCornerShape(
                                            topRight = COLUMN_RADIUS.dp,
                                            topLeft = COLUMN_RADIUS.dp
                                        ).toVicoShape()
                                    }
                                )
                            }
                        ),
                        mergeMode = {
                            ColumnCartesianLayer.MergeMode.stacked()
                        },
                        rangeProvider = remember { CartesianLayerRangeProvider.fixed(minY = -10.0, maxY = 10.0) }
                    ),
                    marker = remember {
                        DefaultCartesianMarker(
                            label = TextComponent(),
                            guideline = LineComponent(
                                fill = Fill.Black,
                                thicknessDp = 2f,
                            )
                        )
                    },
                    persistentMarkers = {
                        DefaultCartesianMarker(
                            label = TextComponent(
                                textSizeSp = 8f,
                                padding = Insets(allDp = 4f),
                                margins = Insets(bottomDp = 4f),
                                background = ShapeComponent(
                                    fill = fill(Colors.yellow_500),
                                    strokeFill = Fill.Black,
                                    strokeThicknessDp = 1f,
                                    shape = CircleShape.toVicoShape(),
                                    shadow = Shadow(radiusDp = 2f)
                                )
                            ),
                            labelPosition = DefaultCartesianMarker.LabelPosition.AbovePoint,
                            valueFormatter = { _, _ ->
                                "Mon anniv' :3"
                            },
                        ) at 1
                    },
                    legend = VerticalLegend(
                        items = {
                            colors.forEachIndexed { index, color ->
                                add(
                                    LegendItem(
                                        label = when (index) {
                                            0 -> "produced"
                                            else -> "consumed"
                                        },
                                        labelComponent = TextComponent(),
                                        icon = ShapeComponent(
                                            fill = fill(color),
                                            shape = CircleShape.toVicoShape()
                                        ),
                                    )
                                )
                            }
                        }
                    ),
                    fadingEdges = remember {
                        FadingEdges(
                            widthDp = COLUMN_WIDTH_DP,
                            visibilityThresholdDp = Dp.VisibilityThreshold.value
                        )
                    },
                    startAxis = VerticalAxis.rememberStart(
                        title = "Consumption",
                        titleComponent = TextComponent(),
                        guideline = rememberAxisGuidelineComponent(),
                        label = TextComponent(textSizeSp = 10f),
                        valueFormatter = remember {
                            CartesianValueFormatter.decimal(
                                decimalFormat = DecimalFormat("#.## kWh;−#.## kWh")
                            )
                        }
                    ),
                    endAxis = VerticalAxis.rememberEnd(
                        label = null,
                        tick = null,
                        guideline = rememberLineComponent(),
                        itemPlacer = remember { VerticalAxis.ItemPlacer.count(count = { 1 }) },
                    ),
                    bottomAxis = HorizontalAxis.rememberBottom(
                        valueFormatter = remember {
                            CartesianValueFormatter { _, value, _ ->
                                val time = Calendar.getInstance().apply {
                                    set(Calendar.MONTH, value.toInt())
                                }.time

                                DateFormat.format("MMM", time).toString()
                            }
                        },
                    ),
                ),
                modelProducer = modelProducer,
            )
        }
    }
}