package ru.apmgor.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.apmgor.data.model.AlertsForecastDb
import ru.apmgor.data.model.CityDb
import ru.apmgor.data.model.CurrentForecastDb
import ru.apmgor.data.model.DailyForecastDb
import ru.apmgor.data.model.ForecastDb
import ru.apmgor.data.model.HourlyForecastDb
import ru.apmgor.data.model.MinutelyForecastDb

@Dao
interface ForecastLocalDataSource : AddCityContract {

    @Transaction
    @Query("SELECT * FROM city")
    fun getForecastFromDb(): Flow<ForecastDb?>

    @Transaction
    suspend fun addForecastToDb(
        cityDb: CityDb,
        currentForecastDb: CurrentForecastDb,
        listAlerts: List<AlertsForecastDb>,
        listMinutely: List<MinutelyForecastDb>,
        listHourly: List<HourlyForecastDb>,
        listDaily: List<DailyForecastDb>
    ) {
        addCityToDb(cityDb)
        addCurrentForecastToDb(currentForecastDb)
        addAlertsForecastToDb(listAlerts)
        addMinutelyForecastToDb(listMinutely)
        addHourlyForecastToDb(listHourly)
        addDailyForecastToDb(listDaily)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addCityToDb(cityDb: CityDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrentForecastToDb(currentForecastDb: CurrentForecastDb)

    @Transaction
    suspend fun addAlertsForecastToDb(listAlerts: List<AlertsForecastDb>) {
        deleteAlerts()
        addAlerts(listAlerts)
    }

    @Insert
    suspend fun addAlerts(list: List<AlertsForecastDb>)

    @Query("DELETE FROM alerts")
    suspend fun deleteAlerts()

    @Transaction
    suspend fun addMinutelyForecastToDb(listMinutely: List<MinutelyForecastDb>) {
        deleteMinutely()
        addMinutely(listMinutely)
    }

    @Insert
    suspend fun addMinutely(list: List<MinutelyForecastDb>)

    @Query("DELETE FROM minutely")
    suspend fun deleteMinutely()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHourlyForecastToDb(list: List<HourlyForecastDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDailyForecastToDb(list: List<DailyForecastDb>)
}