package ru.apmgor.domain.usecases

import kotlinx.coroutines.flow.map
import ru.apmgor.domain.repo.ForecastRepository
import javax.inject.Inject

class GetAlertsUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {

    operator fun invoke() = forecastRepository
        .forecast.map { it?.alertsForecast }
}