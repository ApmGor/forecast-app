package ru.apmgor.data.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.apmgor.data.datasource.UnitsDataSource
import ru.apmgor.data.mappers.toListUnit
import ru.apmgor.data.mappers.toUnitsDb
import ru.apmgor.domain.model.Units
import ru.apmgor.domain.repo.UnitsRepository
import javax.inject.Inject

class UnitsRepositoryImpl @Inject constructor(
    private val unitsDataSource: UnitsDataSource,
    mainScope: CoroutineScope
) : UnitsRepository {


    override val units: Flow<List<Units>> =
        unitsDataSource.getUnitsFromDb().map { unit ->
            unit.toListUnit()
        }.stateIn(
            mainScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    override suspend fun addUnits(units: List<Units>) {
        unitsDataSource.addUnitsToDb(units.toUnitsDb())
    }
}