package ru.apmgor.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ru.apmgor.data.R
import ru.apmgor.domain.model.Forecast
import ru.apmgor.domain.model.HourlyList
import ru.apmgor.presentation.common.ui.Content
import ru.apmgor.presentation.common.ui.Error
import ru.apmgor.presentation.common.ui.Loading
import ru.apmgor.presentation.common.ui.NoContent
import ru.apmgor.presentation.main.components.CurrentForecastAddInfo
import ru.apmgor.presentation.main.components.CurrentForecastInfo
import ru.apmgor.presentation.main.components.DailyForecastAddInfo
import ru.apmgor.presentation.main.components.DailyForecastInfo
import ru.apmgor.presentation.main.components.HourlyForecastInfo
import ru.apmgor.presentation.main.components.NoContentInfo
import ru.apmgor.presentation.main.components.PrecipitationInfo
import ru.apmgor.presentation.main.components.chart.PrecipitationChart
import ru.apmgor.presentation.search.getToastMessage

@Composable
fun MainScreen(
    tempUnits: Array<String>,
    windUnits: Array<String>,
    pressureUnits: Array<String>,
    precipitationUnits: Array<String>,
    distUnits: Array<String>,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    navToAlerts: () -> Unit
) {

    val mainState by viewModel.mainState.collectAsStateWithLifecycle(NoContent)
    val units by viewModel.units.collectAsStateWithLifecycle(emptyList())
    val city by viewModel.city.collectAsStateWithLifecycle(null)
    val context = LocalContext.current
    val windNames = stringArrayResource(id = R.array.wind_names)
    val windDir = stringArrayResource(id = R.array.wind_directions)
    var tabNumber by rememberSaveable { mutableStateOf(DAILY_ADD_INFO_CLOSE) }

    if (city == null) {
        NoContentInfo()
    } else {
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }
        fun refresh() = refreshScope.launch {
            refreshing = true
            city?.let {
                viewModel.addForecast(it)
            }
            refreshing = false
        }
        val pullRefreshState = rememberPullRefreshState(refreshing, ::refresh)

        when (val state = mainState) {
            NoContent -> {}
            is Loading -> Loading()
            is Content -> {
                Box(Modifier.pullRefresh(pullRefreshState)) {
                    Main(state.value, modifier) {
                        currentForecast?.let { cf ->
                            CurrentForecastInfo(
                                tempUnits[units[0].value],
                                cf,
                                viewModel.alertsButtonString,
                                windNames,
                                navToAlerts = navToAlerts
                            )
                        }
                        minutelyForecast.also { mf ->
                            PrecipitationInfo(mf)
                            if (mf.isNotEmpty() && mf.any { it.precipitation != 0.0f }) {
                                PrecipitationChart(
                                    precipitationUnit = precipitationUnits[units[3].value],
                                    minutely = mf
                                )
                            }
                        }
                        currentForecast?.let { cf ->
                            CurrentForecastAddInfo(
                                tempUnit = tempUnits[units[0].value],
                                windUnit = windUnits[units[1].value],
                                pressureUnit = pressureUnits[units[2].value],
                                distUnit = distUnits[units[4].value],
                                windDir = windDir,
                                current = cf
                            )
                        }
                        hourlyForecast.also { hf: List<HourlyList<String>> ->
                            HourlyForecastInfo(
                                tempUnit = tempUnits[units[0].value],
                                hourly = hf
                            )
                        }
                        dailyForecast.also { df ->
                            if (tabNumber == DAILY_ADD_INFO_CLOSE) {
                                DailyForecastInfo(
                                    tempUnit = tempUnits[units[0].value],
                                    daily = df,
                                    openDailyForecastAddInfo = { tabNumber = it }
                                )
                            } else {
                                DailyForecastAddInfo(
                                    tabNumber = tabNumber,
                                    tempUnit = tempUnits[units[0].value],
                                    precipitationUnit = precipitationUnits[units[3].value],
                                    windUnit = windUnits[units[1].value],
                                    pressureUnit = pressureUnits[units[2].value],
                                    windNames = windNames,
                                    windDir = windDir,
                                    daily = df,
                                    setSelectedTab = { tabNumber = it }
                                )
                            }
                        }
                    }
                    PullRefreshIndicator(
                        refreshing,
                        pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
                }
            }

            is Error -> {
                getToastMessage(context, state.error.localizedMessage)
            }
        }
    }
}

const val DAILY_ADD_INFO_CLOSE = -1

@Composable
inline fun Main(
    forecast: Forecast,
    modifier: Modifier = Modifier,
    content: @Composable Forecast.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        forecast.content()
    }
}

