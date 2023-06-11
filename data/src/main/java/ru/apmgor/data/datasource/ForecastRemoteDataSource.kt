package ru.apmgor.data.datasource

import ru.apmgor.data.model.ForecastApi
import ru.apmgor.domain.model.Coordinates


interface ForecastRemoteDataSource {
    suspend fun getForecastFromApi(coordinates: Coordinates): ForecastApi
}