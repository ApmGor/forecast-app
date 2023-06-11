package ru.apmgor.domain.usecases

import ru.apmgor.domain.model.City
import ru.apmgor.domain.repo.CityRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {

    suspend operator fun invoke(cityName: String): List<City> =
        repository.getCities(cityName)
}