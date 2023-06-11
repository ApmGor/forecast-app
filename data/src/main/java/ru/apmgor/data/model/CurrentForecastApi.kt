package ru.apmgor.data.model

import androidx.annotation.Keep

@Keep
data class CurrentForecastApi(
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val uvi: Double,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val weather: List<WeatherApi>
)
