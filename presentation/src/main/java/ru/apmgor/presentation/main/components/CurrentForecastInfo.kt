package ru.apmgor.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.apmgor.data.R
import ru.apmgor.domain.model.CurrentForecast
import ru.apmgor.presentation.common.ui.CompositionLocalMidM3

@Composable
fun CurrentForecastInfo(
    tempUnit: String,
    curr: CurrentForecast,
    alertsButtonString: String,
    windNames: Array<String>,
    modifier: Modifier = Modifier,
    navToAlerts: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .heightIn(200.dp)
            .padding(vertical = 30.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = curr.weather.weatherIcon),
                contentDescription = stringResource(R.string.weather_image),
                modifier = Modifier
                    .size(width = 40.dp, height = 27.dp)
            )
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = curr.weather.weatherDesc,
                    fontSize = 14.sp
                )
                CompositionLocalMidM3 {
                    Text(
                        text = windNames[curr.wind.windNameIndex],
                        fontSize = 12.sp
                    )
                }
            }
        }
        Text(
            text = stringResource(
                id = R.string.current_temp,
                curr.temp,
                tempUnit
            ),
            fontSize = 70.sp
        )
        CompositionLocalMidM3 {
            Text(
                text = stringResource(
                    id = R.string.current_feel_like,
                    curr.feelsLike,
                    tempUnit
                ),
                fontSize = 12.sp
            )
        }
        if (alertsButtonString.isNotBlank()) {
            Button(
                onClick = navToAlerts,
                modifier = Modifier.padding(top = 30.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = alertsButtonString,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}