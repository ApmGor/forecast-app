package ru.apmgor.data.model

import androidx.annotation.Keep

@Keep
data class AlertsForecastApi(
    val event: String,
    val start: Long,
    val end: Long,
    val description: String
)
