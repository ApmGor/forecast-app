package ru.apmgor.domain.model

data class DailyForecast(
    val dt: String,
    val weather: Weather,
    val tempDay: Int,
    val tempNight: Int,
    val rain: Double?,
    val pop: Int,
    val wind: Wind,
    val pressure: Int,
    val humidity: Int,
    val uvi: Double,
    val sunrise: String,
    val sunset: String
)
