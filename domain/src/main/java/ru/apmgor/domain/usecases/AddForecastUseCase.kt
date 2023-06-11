package ru.apmgor.domain.usecases

import ru.apmgor.domain.model.City
import ru.apmgor.domain.repo.ForecastRepository
import javax.inject.Inject

class AddForecastUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {

    suspend operator fun invoke(city: City) {
        forecastRepository.addForecast(city)
    }
}