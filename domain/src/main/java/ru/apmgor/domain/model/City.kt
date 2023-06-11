package ru.apmgor.domain.model

data class City(
    val name: String,
    val country: String,
    val state: String?,
    val coordinates: Coordinates,
    val timezoneOffset: Long? = null
)
