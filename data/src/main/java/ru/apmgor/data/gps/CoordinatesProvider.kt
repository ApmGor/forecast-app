package ru.apmgor.data.gps

import kotlinx.coroutines.flow.Flow
import ru.apmgor.data.model.CoordinatesGps

interface CoordinatesProvider {

    fun getCoordinates(): Flow<CoordinatesGps>
}