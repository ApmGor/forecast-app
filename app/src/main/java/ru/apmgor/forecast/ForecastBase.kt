package ru.apmgor.forecast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.apmgor.forecast.Routes.ALERTS
import ru.apmgor.forecast.Routes.MAIN
import ru.apmgor.forecast.Routes.SETTINGS
import ru.apmgor.presentation.alerts.AlertsScreen
import ru.apmgor.presentation.common.ui.IconButton
import ru.apmgor.presentation.main.MainScreen
import ru.apmgor.presentation.search.SearchScreen
import ru.apmgor.presentation.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastBase() {
    var currentRoute by remember { mutableStateOf(MAIN.name) }
    val navController = rememberNavController()
    var title by remember { mutableStateOf("") }

    val tempUnits = stringArrayResource(id = R.array.temp_units)
    val windUnits = stringArrayResource(id = R.array.wind_units)
    val pressureUnits = stringArrayResource(id = R.array.pressure_units)
    val precipitationUnits = stringArrayResource(id = R.array.precipitation_units)
    val distUnits = stringArrayResource(id = R.array.dist_units)
    val timeUnits = stringArrayResource(id = R.array.time_units)

    Box(Modifier.fillMaxSize()) {
        if (currentRoute == MAIN.name) {
            SearchScreen {
                navController.navigate(SETTINGS.name)
            }
        }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        if (currentRoute != MAIN.name) {
                            IconButton(
                                image = Icons.Default.ArrowBack,
                                descId = R.string.back_arrow_desc,
                                onClick = { navController.popBackStack() }
                            )
                        }
                    }
                )
            }
        ) { pv ->

            NavHost(
                navController = navController,
                startDestination = MAIN.name,
                modifier = Modifier
                    .padding(pv)
            ) {

                composable(MAIN.name) {
                    currentRoute = it.destination.route ?: ""
                    title = ""
                    MainScreen(
                        tempUnits,
                        windUnits,
                        pressureUnits,
                        precipitationUnits,
                        distUnits
                    ) { navController.navigate(ALERTS.name) }
                }
                composable(ALERTS.name) {
                    currentRoute = it.destination.route ?: ""
                    title = stringResource(id = R.string.warning_title)
                    AlertsScreen()
                }
                composable(SETTINGS.name) {
                    currentRoute = it.destination.route ?: ""
                    title = stringResource(id = R.string.settings_title)
                    SettingsScreen(
                        tempUnits,
                        windUnits,
                        pressureUnits,
                        precipitationUnits,
                        distUnits,
                        timeUnits
                    )
                }
            }
        }
    }
}

enum class Routes {
    MAIN, ALERTS, SETTINGS
}