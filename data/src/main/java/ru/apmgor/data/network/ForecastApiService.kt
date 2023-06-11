package ru.apmgor.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.apmgor.data.model.ForecastApi

interface ForecastApiService {
    @GET(API_FORECAST_URL)
    suspend fun getForecast(
        @QueryMap options: Map<String, String>
    ) : ForecastApi

    companion object {
        private const val API_FORECAST_URL = "data/2.5/onecall"
    }
}