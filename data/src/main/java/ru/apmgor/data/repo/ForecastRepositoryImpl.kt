package ru.apmgor.data.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.apmgor.data.datasource.ForecastLocalDataSource
import ru.apmgor.data.datasource.ForecastRemoteDataSource
import ru.apmgor.data.datasource.UnitsDataSource
import ru.apmgor.data.mappers.toCityDb
import ru.apmgor.data.mappers.toCurrentForecastDb
import ru.apmgor.data.mappers.toForecast
import ru.apmgor.data.mappers.toListAlertsForecastDb
import ru.apmgor.data.mappers.toListDailyForecastDb
import ru.apmgor.data.mappers.toListHourlyForecastDb
import ru.apmgor.data.mappers.toListMinutelyForecastDb
import ru.apmgor.domain.model.City
import ru.apmgor.domain.repo.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastLocalDataSource: ForecastLocalDataSource,
    private val forecastRemoteDataSource: ForecastRemoteDataSource,
    unitsDataSource: UnitsDataSource,
    mainScope: CoroutineScope
) : ForecastRepository {


    override val forecast = combine(
        forecastLocalDataSource.getForecastFromDb(),
        unitsDataSource.getUnitsFromDb()
    ) { forecast, units ->
        forecast?.toForecast(units)
    }.stateIn(
        mainScope,
        SharingStarted.Lazily,
        null
    )

    override suspend fun addForecast(city: City) {
        val forecast = forecastRemoteDataSource
            .getForecastFromApi(city.coordinates)
        val cityDb = city.copy(timezoneOffset = forecast.timezone_offset).toCityDb()
        val currentDb = forecast.toCurrentForecastDb()
        val alertsDb = forecast.alerts?.toListAlertsForecastDb() ?: emptyList()
        val minutelyDb = forecast.minutely?.toListMinutelyForecastDb() ?: emptyList()
        val hourlyDb = forecast.hourly.toListHourlyForecastDb()
        val dailyDb = forecast.daily.toListDailyForecastDb()
        forecastLocalDataSource.addForecastToDb(
            cityDb, currentDb, alertsDb, minutelyDb, hourlyDb, dailyDb
        )
    }
}