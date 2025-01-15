package com.example.testcharts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.testcharts.ui.composecharts.BarChartCard
import com.example.testcharts.ui.composecharts.LineChartCard
import com.example.testcharts.ui.composecharts.PieChartCard
import com.example.testcharts.ui.core.ScreenComponent
import com.example.testcharts.ui.vico.VicoBarChartCard
import com.example.testcharts.ui.vico.VicoCombinedChartCard
import com.example.testcharts.ui.vico.VicoLineChartCard
import kotlinx.coroutines.launch

private enum class Tabs {
    COMPOSE_CHARTS,
    VICO;
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val pagerState = rememberPagerState { Tabs.entries.size }
            val scope = rememberCoroutineScope()

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    TabRow(selectedTabIndex = pagerState.currentPage) {
                        Tabs.entries.forEachIndexed { index, tab ->
                            Tab(
                                text = {
                                    Text(tab.name)
                                },
                                selected = pagerState.currentPage == tab.ordinal,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                            )
                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false,
                    ) { index ->
                        when (index) {
                            Tabs.COMPOSE_CHARTS.ordinal -> {
                                ComposeChartsScreen()
                            }

                            Tabs.VICO.ordinal -> {
                                VicoScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ComposeChartsScreen() {
    ScreenComponent {
        item {
            BarChartCard()
        }
        item {
            LineChartCard()
        }
        item {
            PieChartCard()
        }
    }
}

@Composable
fun VicoScreen() {
    ScreenComponent {
        item {
            VicoBarChartCard()
        }
        item {
            VicoLineChartCard()
        }
        item {
            VicoCombinedChartCard()
        }
    }
}