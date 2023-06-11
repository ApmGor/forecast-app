package ru.apmgor.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.apmgor.data.R
import ru.apmgor.domain.model.HourlyForecast
import ru.apmgor.domain.model.HourlyList
import ru.apmgor.domain.model.Sunrise
import ru.apmgor.domain.model.Sunset
import ru.apmgor.presentation.common.ui.CompositionLocalMidM3

@Composable
fun HourlyForecastInfo(
    tempUnit: String,
    hourly: List<HourlyList<String>>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(hourly) {
            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(70.dp)
                        .padding(vertical = 8.dp)
                ) {
                    CompositionLocalMidM3 {
                        Text(text = it.dt, fontSize = 10.sp)
                    }

                    when(it) {
                        is HourlyForecast -> {
                            ImageTextBlock(
                                painterId = { it.weatherIconDay },
                                string = {
                                    stringResource(id = R.string.current_temp, it.temp, tempUnit)
                                }
                            )
                        }
                        is Sunset -> {
                            ImageTextBlock(
                                painterId = {
                                    if (isSystemInDarkTheme())
                                        it.weatherIconNight
                                    else
                                        it.weatherIconDay
                                },
                                string = { stringResource(id = it.name) }
                            )
                        }
                        is Sunrise -> {
                            ImageTextBlock(
                                painterId = {
                                    if (isSystemInDarkTheme())
                                        it.weatherIconNight
                                    else
                                        it.weatherIconDay
                                },
                                string = { stringResource(id = it.name) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private inline fun ImageTextBlock(
    painterId: @Composable () -> Int,
    string: @Composable () -> String
) {
    Image(
        painter = painterResource(id = painterId()),
        contentDescription = stringResource(id = R.string.weather_image),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .size(width = 30.dp, height = 20.dp)
    )
    Text(
        text = string(),
        fontSize = 14.sp
    )
}