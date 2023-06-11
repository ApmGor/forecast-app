package ru.apmgor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "units")
data class UnitsDb(
    @PrimaryKey
    val id: Int = 0,
    val tempUnits: Int = 0,
    val windUnits: Int = 0,
    val pressureUnits: Int = 0,
    val precipitationUnits: Int = 0,
    val distUnits: Int = 0,
    val timeUnits: Int = 0
)