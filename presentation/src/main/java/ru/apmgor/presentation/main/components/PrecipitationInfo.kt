package ru.apmgor.presentation.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.apmgor.data.R
import ru.apmgor.domain.model.MinutelyForecast

@Composable
fun PrecipitationInfo(
    minutely: List<MinutelyForecast>,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources


    Row(modifier = modifier.padding(bottom = 8.dp)) {
        when {
            minutely.isEmpty() ||
                    minutely.all { it.precipitation == 0.0f } ->
                Text(
                    text = stringResource(id = R.string.no_precipitation),
                    fontSize = 14.sp
                )

            minutely[0].precipitation != 0.0f &&
                    minutely[minutely.size - 1].precipitation != 0.0f ->
                Text(
                    text = stringResource(id = R.string.full_hour_precipitation),
                    fontSize = 14.sp
                )

            minutely[0].precipitation != 0.0f -> {
                val quantity = minutely.indexOf(minutely.first { it.precipitation == 0.0f })
                Text(
                    text = resources.getQuantityString(
                        R.plurals.number_of_minutes_1,
                        quantity,
                        quantity
                    ),
                    fontSize = 14.sp
                )
            }

            else -> {
                val quantity = minutely.indexOf(minutely.first { it.precipitation != 0.0f })
                Text(
                    text = resources.getQuantityString(
                        R.plurals.number_of_minutes_2,
                        quantity,
                        quantity
                    ),
                    fontSize = 14.sp
                )
            }
        }
    }
}