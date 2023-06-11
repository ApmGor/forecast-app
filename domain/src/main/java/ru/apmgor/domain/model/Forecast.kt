package ru.apmgor.domain.model

data class Forecast(
    val city: City?,
    val currentForecast: CurrentForecast?,
    val alertsForecast: List<AlertsForecast>,
    val minutelyForecast: List<MinutelyForecast>,
    val hourlyForecast: List<HourlyList<String>>,
    val dailyForecast: List<DailyForecast>
)
