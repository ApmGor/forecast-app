package ru.apmgor.domain.model

import androidx.annotation.DrawableRes

data class Weather(
    val weatherDesc: String,
    @DrawableRes val weatherIcon: Int
)
