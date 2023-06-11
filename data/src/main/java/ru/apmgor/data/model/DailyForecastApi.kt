package ru.apmgor.data.model

import androidx.annotation.Keep

@Keep
data class DailyForecastApi(
    val dt: Long,
    val weather: List<WeatherApi>,
    val temp: TemperatureApi,
    val rain: Double?,
    val pop: Double,
    val wind_speed: Double,
    val wind_deg: Int,
    val pressure: Int,
    val humidity: Int,
    val uvi: Double,
    val sunrise: Long,
    val sunset: Long
)
