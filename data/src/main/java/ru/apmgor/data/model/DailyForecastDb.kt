package ru.apmgor.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dailies")
data class DailyForecastDb(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "city_id")
    val cityId: Int = 0,
    val dt: Long,
    val weatherDesc: String,
    val weatherIcon: String,
    val tempDay: Double,
    val tempNight: Double,
    val rain: Double?,
    val pop: Double,
    val windSpeed: Double,
    val windDeg: Int,
    val pressure: Int,
    val humidity: Int,
    val uvi: Double,
    val sunrise: Long,
    val sunset: Long
)
