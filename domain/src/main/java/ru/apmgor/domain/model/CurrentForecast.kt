package ru.apmgor.domain.model

data class CurrentForecast(
    val temp: Int,
    val feelsLike: Int,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Int,
    val uvi: Double,
    val visibility: Int,
    val wind: Wind,
    val weather: Weather
)
