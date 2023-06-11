package ru.apmgor.data.model

import androidx.annotation.Keep

@Keep
data class WeatherApi(
    val description: String,
    val icon: String
)
