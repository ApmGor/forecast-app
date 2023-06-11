package ru.apmgor.data.datasource

import ru.apmgor.data.model.CityDb

interface AddCityContract {
    suspend fun addCityToDb(cityDb: CityDb)
}