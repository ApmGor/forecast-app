package ru.apmgor.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    val name: String,
    val country: String,
    val state: String?,
    val lat: Double,
    val lon: Double,
    val timezoneOffset: Long? = null
)
