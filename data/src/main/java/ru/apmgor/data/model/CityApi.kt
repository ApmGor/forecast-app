package ru.apmgor.data.model

import androidx.annotation.Keep

@Keep
data class CityApi(
    val name: String,
    val local_names: LocalNames?,
    val country: String,
    val state: String?,
    val lat: Double,
    val lon: Double
)

@Keep
data class LocalNames(val ru: String?)