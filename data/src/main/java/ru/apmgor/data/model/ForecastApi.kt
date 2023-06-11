package ru.apmgor.data.model

import androidx.annotation.Keep
import ru.apmgor.forecast.data.model.HourlyForecastApi

@Keep
data class ForecastApi(
    val timezone_offset: Long,
    val current: CurrentForecastApi,
    val minutely: List<MinutelyForecastApi>?,
    val hourly: List<HourlyForecastApi>,
    val daily: List<DailyForecastApi>,
    val alerts: List<AlertsForecastApi>?
)
