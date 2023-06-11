package ru.apmgor.domain.usecases

import ru.apmgor.domain.model.Units
import ru.apmgor.domain.repo.UnitsRepository
import javax.inject.Inject

class AddUnitsUseCase @Inject constructor(
    private val unitsRepository: UnitsRepository
) {

    suspend operator fun invoke(units: List<Units>) {
        unitsRepository.addUnits(units)
    }
}