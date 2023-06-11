package ru.apmgor.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.apmgor.data.datasource.CityLocalDataSource
import ru.apmgor.data.datasource.ForecastLocalDataSource
import ru.apmgor.data.datasource.UnitsDataSource
import ru.apmgor.data.model.AlertsForecastDb
import ru.apmgor.data.model.CityDb
import ru.apmgor.data.model.CurrentForecastDb
import ru.apmgor.data.model.DailyForecastDb
import ru.apmgor.data.model.HourlyForecastDb
import ru.apmgor.data.model.MinutelyForecastDb
import ru.apmgor.data.model.UnitsDb

private const val DB_VERSION = 1

@Database(
    entities = [
        CityDb::class,
        CurrentForecastDb::class,
        AlertsForecastDb::class,
        UnitsDb::class,
        MinutelyForecastDb::class,
        HourlyForecastDb::class,
        DailyForecastDb::class
    ],
    version = DB_VERSION
)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun getCityLocalDataSource(): CityLocalDataSource

    abstract fun getForecastLocalDataSource(): ForecastLocalDataSource

    abstract fun getUnitsDataSource(): UnitsDataSource

    companion object {
        private var INSTANCE: ForecastDatabase? = null
        private const val DB_NAME = "forecast.db"
        private val LOCK = Any()

        fun getInstance(application: Context): ForecastDatabase {
            return INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: run {
                    val db = Room.databaseBuilder(
                        application,
                        ForecastDatabase::class.java,
                        DB_NAME
                    ).createFromAsset("database/forecast_db.db")
                        .build()
                    INSTANCE = db
                    db
                }
            }
        }
    }
}