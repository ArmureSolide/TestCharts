package com.example.testcharts.ui.vico

import android.graphics.Typeface
import android.text.format.DateFormat
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.testcharts.ui.core.ChartCard
import com.example.testcharts.ui.theme.Colors
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.layer.continuous
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.toVicoShape
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker.ValueFormatter
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.component.Shadow
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import java.util.Calendar

private val lineColor = Colors.green_500
private val areaColor = Colors.green_700
private val markerColor = Colors.green_300

@Composable
fun VicoLineChartCard(modifier: Modifier = Modifier) {
    val data = remember {
        CartesianChartModel(
            LineCartesianLayerModel.build {
                series(*Array(31) { (-9..9).random() })
            }
        )
    }

    ChartCard(modifier = modifier) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(
                            LineCartesianLayer.LineFill.single(
                                fill(lineColor)
                            ),
                            areaFill = LineCartesianLayer.AreaFill.single(
                                fill = fill(
                                    ShaderProvider.verticalGradient(
                                        areaColor.toArgb(),
                                        Color.Transparent.toArgb()
                                    )
                                ),
                                splitY = { -10 }
                            ),
                            pointConnector = LineCartesianLayer.PointConnector.cubic(curvature = 0.2f),
                            stroke = LineCartesianLayer.LineStroke.continuous(
                                thickness = 4.dp,
                                cap = StrokeCap.Round,
                            )
                        ),
                    ),
                    rangeProvider = remember { CartesianLayerRangeProvider.fixed(minY = -10.0, maxY = 10.0) }
                ),
                marker = remember {
                    DefaultCartesianMarker(
                        label = TextComponent(
                            color = markerColor.toArgb(),
                            margins = Insets(10f),
                            typeface = Typeface.DEFAULT_BOLD,
                        ),
                        labelPosition = DefaultCartesianMarker.LabelPosition.AbovePoint,
                        valueFormatter = ValueFormatter.default(colorCode = false),
                        indicatorSizeDp = 20f,
                        indicator = { _ ->
                            ShapeComponent(
                                shape = CircleShape.toVicoShape(),
                                fill = fill(markerColor),
                                strokeFill = fill(Color.White),
                                strokeThicknessDp = 2f,
                                shadow = Shadow(radiusDp = 2f)
                            )
                        },
                    )
                },
                endAxis = VerticalAxis.rememberEnd(),
                bottomAxis = HorizontalAxis.rememberBottom(
                    valueFormatter = remember {
                        CartesianValueFormatter { _, value, _ ->
                            val time = Calendar.getInstance().apply {
                                set(Calendar.MONTH, 0)
                                set(Calendar.DAY_OF_MONTH, value.toInt())
                            }.time

                            DateFormat.format("d MMM", time).toString()
                        }
                    },
                ),
            ),
            model = data,
        )
    }
}