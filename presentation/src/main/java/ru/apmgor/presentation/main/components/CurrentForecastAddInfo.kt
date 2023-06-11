package ru.apmgor.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.apmgor.data.R
import ru.apmgor.domain.model.CurrentForecast

@Composable
fun CurrentForecastAddInfo(
    tempUnit: String,
    windUnit: String,
    pressureUnit: String,
    distUnit: String,
    windDir: Array<String>,
    current: CurrentForecast,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(
                            R.string.current_wind,
                            current.wind.windSpeed,
                            windUnit,
                            windDir[current.wind.windDirectionIndex]
                        ),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(
                            id = if (isSystemInDarkTheme())
                                current.wind.windArrowIconNight
                            else
                                current.wind.windArrowIconDay
                        ),
                        contentDescription = stringResource(R.string.arrow_wind_direction),
                        modifier = Modifier
                            .size(11.dp)
                    )
                }
                Text(
                    text = stringResource(
                        R.string.current_pressure,
                        current.pressure,
                        pressureUnit
                    ),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 16.dp))

            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(
                        R.string.current_humidity,
                        current.humidity
                    ),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp
                )
                Text(
                    text = stringResource(
                        R.string.current_visibility,
                        current.visibility,
                        distUnit
                    ),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 16.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(
                        R.string.current_uvi,
                        current.uvi
                    ),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp
                )
                Text(
                    text = stringResource(
                        R.string.current_dew_point,
                        current.dewPoint,
                        tempUnit
                    ),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}