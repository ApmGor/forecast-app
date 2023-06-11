package ru.apmgor.data.datasource

import ru.apmgor.data.model.CityApi

interface CityRemoteDataSource {

    suspend fun getCityFromApi(): CityApi

    suspend fun getCitiesFromApi(cityName: String): List<CityApi>
}