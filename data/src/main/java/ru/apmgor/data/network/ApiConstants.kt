package ru.apmgor.data.network

import ru.apmgor.data.BuildConfig

object ApiConstants {
    const val API_KEY = BuildConfig.OPEN_WEATHER_API_KEY
    const val LIMIT = "5"
    const val CITY_QP = "q"
    const val LIMIT_QP = "limit"
    const val API_KEY_QP = "appid"
    const val LATITUDE_QP = "lat"
    const val LONGITUDE_QP = "lon"
    const val UNITS = "metric"
    const val LANGUAGE = "ru"
    const val UNITS_QP = "units"
    const val LANGUAGE_QP = "lang"
}