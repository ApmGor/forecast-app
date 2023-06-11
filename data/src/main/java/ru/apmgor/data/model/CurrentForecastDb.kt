package ru.apmgor.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current")
data class CurrentForecastDb(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "city_id")
    val cityId: Int = 0,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val weatherDesc: String,
    val weatherIcon: String
)
