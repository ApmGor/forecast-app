package ru.apmgor.domain.usecases

import ru.apmgor.domain.repo.UnitsRepository
import javax.inject.Inject

class GetUnitsUseCase @Inject constructor(
    private val unitsRepository: UnitsRepository
) {

    operator fun invoke() = unitsRepository.units
}