package ru.apmgor.domain.model

data class AlertsForecast(
    val event: String,
    val start: String,
    val end: String,
    val description: String
)