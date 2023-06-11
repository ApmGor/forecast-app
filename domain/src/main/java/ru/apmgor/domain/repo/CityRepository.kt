package ru.apmgor.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.apmgor.domain.model.City

interface CityRepository {

    val city: Flow<City?>

    suspend fun getCities(cityName: String): List<City>

    suspend fun addCity(city: City?)
}