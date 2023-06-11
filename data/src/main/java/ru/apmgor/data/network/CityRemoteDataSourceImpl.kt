package ru.apmgor.data.network

import kotlinx.coroutines.flow.first
import ru.apmgor.data.datasource.CityRemoteDataSource
import ru.apmgor.data.gps.CoordinatesProvider
import ru.apmgor.data.model.CityApi
import ru.apmgor.data.network.ApiConstants.API_KEY
import ru.apmgor.data.network.ApiConstants.API_KEY_QP
import ru.apmgor.data.network.ApiConstants.CITY_QP
import ru.apmgor.data.network.ApiConstants.LATITUDE_QP
import ru.apmgor.data.network.ApiConstants.LIMIT
import ru.apmgor.data.network.ApiConstants.LIMIT_QP
import ru.apmgor.data.network.ApiConstants.LONGITUDE_QP
import javax.inject.Inject

class CityRemoteDataSourceImpl @Inject constructor(
    private val cityApiService: CityApiService,
    private val coordinatesProvider: CoordinatesProvider
) : CityRemoteDataSource {

    override suspend fun getCityFromApi(): CityApi {
        val coordinates = coordinatesProvider
            .getCoordinates()
            .first()
        return cityApiService
            .getCityApi(
                mapOf(
                    LATITUDE_QP to "${coordinates.latitude}",
                    LONGITUDE_QP to "${coordinates.longitude}",
                    LIMIT_QP to LIMIT,
                    API_KEY_QP to API_KEY
                )
            )
            .first()
    }

    override suspend fun getCitiesFromApi(cityName: String): List<CityApi> {
        return cityApiService.getCitiesApi(
            mapOf(
                CITY_QP to cityName,
                LIMIT_QP to LIMIT,
                API_KEY_QP to API_KEY
            )
        )
    }

}