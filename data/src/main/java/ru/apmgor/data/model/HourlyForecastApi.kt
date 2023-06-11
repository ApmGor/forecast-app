package ru.apmgor.forecast.data.model

import androidx.annotation.Keep
import ru.apmgor.data.model.WeatherApi

@Keep
data class HourlyForecastApi(
    val dt: Long,
    val temp: Double,
    val weather: List<WeatherApi>
)
