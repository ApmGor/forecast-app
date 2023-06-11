package ru.apmgor.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.apmgor.data.model.CityDb

@Dao
interface CityLocalDataSource : AddCityContract {

    @Query("SELECT * FROM city LIMIT 1")
    fun getCityFromDb(): Flow<CityDb?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addCityToDb(cityDb: CityDb)
}

