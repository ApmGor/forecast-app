package ru.apmgor.data.network

import ru.apmgor.data.datasource.ForecastRemoteDataSource
import ru.apmgor.data.model.ForecastApi
import ru.apmgor.data.network.ApiConstants.API_KEY
import ru.apmgor.data.network.ApiConstants.API_KEY_QP
import ru.apmgor.data.network.ApiConstants.LANGUAGE
import ru.apmgor.data.network.ApiConstants.LANGUAGE_QP
import ru.apmgor.data.network.ApiConstants.LATITUDE_QP
import ru.apmgor.data.network.ApiConstants.LONGITUDE_QP
import ru.apmgor.data.network.ApiConstants.UNITS
import ru.apmgor.data.network.ApiConstants.UNITS_QP
import ru.apmgor.domain.model.Coordinates
import javax.inject.Inject

class ForecastRemoteDataSourceImpl @Inject constructor(
    private val forecastApiService: ForecastApiService
) : ForecastRemoteDataSource {

    override suspend fun getForecastFromApi(coordinates: Coordinates): ForecastApi {
        return forecastApiService.getForecast(
            mapOf(
                LATITUDE_QP to "${coordinates.latitude}",
                LONGITUDE_QP to "${coordinates.longitude}",
                UNITS_QP to UNITS,
                LANGUAGE_QP to LANGUAGE,
                API_KEY_QP to API_KEY
            )
        )
    }
}