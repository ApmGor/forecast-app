package ru.apmgor.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ForecastDb(
    @Embedded val city: CityDb?,
    @Relation(
        parentColumn = "id",
        entityColumn = "city_id"
    )
    val current: CurrentForecastDb?,
    @Relation(
        parentColumn = "id",
        entityColumn = "city_id"
    )
    val alerts: List<AlertsForecastDb>,
    @Relation(
        parentColumn = "id",
        entityColumn = "city_id"
    )
    val minutely: List<MinutelyForecastDb>,
    @Relation(
        parentColumn = "id",
        entityColumn = "city_id"
    )
    val hourly: List<HourlyForecastDb>,
    @Relation(
        parentColumn = "id",
        entityColumn = "city_id"
    )
    val daily: List<DailyForecastDb>
)
