package ru.apmgor.data.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.apmgor.data.datasource.CityLocalDataSource
import ru.apmgor.data.datasource.CityRemoteDataSource
import ru.apmgor.data.mappers.toCity
import ru.apmgor.data.mappers.toCityDb
import ru.apmgor.domain.model.City
import ru.apmgor.domain.repo.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityLocalDataSource: CityLocalDataSource,
    private val cityRemoteDataSource: CityRemoteDataSource,
    mainScope: CoroutineScope
) : CityRepository {

    override val city = cityLocalDataSource
        .getCityFromDb()
        .map { it?.toCity() }
        .stateIn(
            mainScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    override suspend fun getCities(cityName: String): List<City> =
        cityRemoteDataSource.getCitiesFromApi(cityName).map { it.toCity() }

    override suspend fun addCity(city: City?) {
        val value = city?.toCityDb() ?: cityRemoteDataSource.getCityFromApi().toCityDb()
        cityLocalDataSource.addCityToDb(value)
    }
}