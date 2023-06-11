package ru.apmgor.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.apmgor.data.model.UnitsDb

@Dao
interface UnitsDataSource {

    @Query("SELECT * FROM units LIMIT 1")
    fun getUnitsFromDb(): Flow<UnitsDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUnitsToDb(unitsDb: UnitsDb)

}