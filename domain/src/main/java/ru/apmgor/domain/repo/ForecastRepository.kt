package ru.apmgor.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.apmgor.domain.model.City
import ru.apmgor.domain.model.Forecast

interface ForecastRepository {

    val forecast: Flow<Forecast?>

    suspend fun addForecast(city: City)
}