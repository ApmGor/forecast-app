package ru.apmgor.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class AlertsForecastDb(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "city_id")
    val cityId: Int = 0,
    val event: String,
    val start: Long,
    val end: Long,
    val description: String
)
