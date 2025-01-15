package com.example.testcharts.ui.composecharts

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcharts.ui.core.ChartCard
import com.example.testcharts.ui.theme.Colors
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorPosition
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.LineProperties
import ir.ehsannarmani.compose_charts.models.StrokeStyle
import java.util.Calendar

private const val MIN_BAR_VALUE = 15
private const val MAX_BAR_VALUE = 75
private const val BAR_CORNER_RADIUS_DP = 4
private const val BAR_THICKNESS_DP = 8
private const val AXIS_PADDING_DP = 4
private const val AXIS_FONT_SIZE_SP = 10

@Composable
fun BarChartCard(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val data = remember {
        List(7) { day ->
            Bars(
                label = Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_WEEK, day)
                }.time.let {
                    android.text.format.DateFormat.format("E", it).toString()
                },
                values = listOf(
                    Bars.Data(
                        label = "Consumed",
                        value = (MIN_BAR_VALUE..MAX_BAR_VALUE).random().toDouble(),
                        color = Brush.verticalGradient(
                            colors = listOf(
                                Colors.red_700,
                                Colors.red_500,
                                Colors.red_300,
                                Colors.red_100,
                            ),
                        ),
                    ),
                    Bars.Data(
                        label = "Produced",
                        value = (-MAX_BAR_VALUE..-MIN_BAR_VALUE).random().toDouble(),
                        color = Brush.verticalGradient(
                            colors = listOf(
                                Colors.green_100,
                                Colors.green_300,
                                Colors.green_500,
                                Colors.green_700,
                            ),
                        ),
                    ),
                ),
            )
        }
    }
    ChartCard(modifier = modifier) {
        ColumnChart(
            data = data,
            barProperties = BarProperties(
                thickness = BAR_THICKNESS_DP.dp,
                cornerRadius = Bars.Data.Radius.Rectangle(
                    topRight = BAR_CORNER_RADIUS_DP.dp,
                    topLeft = BAR_CORNER_RADIUS_DP.dp,
                ),
            ),
            gridProperties = GridProperties(
                xAxisProperties = GridProperties.AxisProperties(
                    style = StrokeStyle.Dashed()
                ),
                yAxisProperties = GridProperties.AxisProperties(
                    enabled = false,
                )
            ),
            labelProperties = LabelProperties(
                padding = AXIS_PADDING_DP.dp,
                textStyle = TextStyle.Default.copy(fontSize = AXIS_FONT_SIZE_SP.sp),
                rotation = LabelProperties.Rotation(
                    mode = LabelProperties.Rotation.Mode.Force,
                    degree = 0f
                ),
                enabled = true,
            ),
            indicatorProperties = HorizontalIndicatorProperties(
                padding = AXIS_PADDING_DP.dp,
                textStyle = TextStyle.Default.copy(fontSize = AXIS_FONT_SIZE_SP.sp),
                position = IndicatorPosition.Horizontal.End,
            ),
            dividerProperties = DividerProperties(
                xAxisProperties = LineProperties(enabled = false)
            ),
            maxValue = 100.0,
            animationMode = AnimationMode.Together { it * 25L },
            animationSpec = tween(300),
            onBarClick = { barData ->
                Toast.makeText(context, "Bar clicked : ${barData.value}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Preview
@Composable
private fun BarChartCardPreview() {
    BarChartCard()
}