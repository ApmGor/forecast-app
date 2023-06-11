package ru.apmgor.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.apmgor.domain.model.Units

interface UnitsRepository {

    val units: Flow<List<Units>>

    suspend fun addUnits(units: List<Units>)
}