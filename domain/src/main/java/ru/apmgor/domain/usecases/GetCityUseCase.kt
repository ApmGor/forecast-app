package ru.apmgor.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.apmgor.domain.model.City
import ru.apmgor.domain.repo.CityRepository
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val repository: CityRepository
) {

    operator fun invoke(): Flow<City?> =
        repository.city
}