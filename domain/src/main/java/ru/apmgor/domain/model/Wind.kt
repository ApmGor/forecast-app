package ru.apmgor.domain.model

import androidx.annotation.DrawableRes

data class Wind(
    val windSpeed: Double,
    val windNameIndex: Int,
    val windDirectionIndex: Int,
    @DrawableRes val windArrowIconDay: Int,
    @DrawableRes val windArrowIconNight: Int
)
