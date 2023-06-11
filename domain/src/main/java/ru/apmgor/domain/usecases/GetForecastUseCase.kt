package ru.apmgor.domain.usecases

import ru.apmgor.domain.repo.ForecastRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {

    operator fun invoke() = forecastRepository.forecast
}