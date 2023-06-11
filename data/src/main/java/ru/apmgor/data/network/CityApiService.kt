package ru.apmgor.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.apmgor.data.model.CityApi


interface CityApiService {

    @GET(API_CITIES_URL)
    suspend fun getCitiesApi(
        @QueryMap options: Map<String, String>
    ): List<CityApi>

    @GET(API_CITY_URL)
    suspend fun getCityApi(
        @QueryMap options: Map<String, String>
    ): List<CityApi>

    companion object {
        private const val API_CITY_URL = "geo/1.0/reverse"
        private const val API_CITIES_URL = "geo/1.0/direct"
    }
}