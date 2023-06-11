package ru.apmgor.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class HourlyList<out T>(val dt: T)

class HourlyForecast<out T>(
    dt: T,
    val temp: Int,
    @DrawableRes val weatherIconDay: Int
) : HourlyList<T>(dt)

class Sunset<out T>(
    dt: T,
    @StringRes val name: Int,
    @DrawableRes val weatherIconDay: Int,
    @DrawableRes val weatherIconNight: Int
) : HourlyList<T>(dt)

class Sunrise<out T>(
    dt: T,
    @StringRes val name: Int,
    @DrawableRes val weatherIconDay: Int,
    @DrawableRes val weatherIconNight: Int
) : HourlyList<T>(dt)