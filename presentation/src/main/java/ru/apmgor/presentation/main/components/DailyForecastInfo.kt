package ru.apmgor.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.apmgor.data.R
import ru.apmgor.domain.model.DailyForecast

@Composable
fun DailyForecastInfo(
    tempUnit: String,
    daily: List<DailyForecast>,
    openDailyForecastAddInfo: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 8.dp)) {
        daily.forEachIndexed { i, it ->
            ListItem(
                headlineContent = { Text(text = it.dt, fontSize = 14.sp) },
                trailingContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${it.tempDay}/${it.tempNight}°$tempUnit",
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                        Image(
                            painter = painterResource(id = it.weather.weatherIcon),
                            contentDescription = stringResource(R.string.weather_image),
                            modifier = Modifier
                                .size(width = 30.dp, height = 20.dp)
                                .padding(start = 8.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.clickable { openDailyForecastAddInfo(i) }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}
