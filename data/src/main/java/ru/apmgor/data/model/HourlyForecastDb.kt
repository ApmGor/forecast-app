package ru.apmgor.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourlies")
data class HourlyForecastDb(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "city_id")
    val cityId: Int = 0,
    val dt: Long,
    val temp: Double,
    val weatherIcon: String
)