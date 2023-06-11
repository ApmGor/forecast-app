package ru.apmgor.data.model

import androidx.annotation.Keep

@Keep
data class TemperatureApi(
    val day: Double,
    val night: Double
)
