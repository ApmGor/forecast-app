package ru.apmgor.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.apmgor.domain.model.AlertsForecast
import ru.apmgor.domain.model.City
import ru.apmgor.domain.model.Forecast
import ru.apmgor.domain.usecases.AddForecastUseCase
import ru.apmgor.domain.usecases.GetCityUseCase
import ru.apmgor.domain.usecases.GetForecastUseCase
import ru.apmgor.domain.usecases.GetUnitsUseCase
import ru.apmgor.presentation.common.ui.Content
import ru.apmgor.presentation.common.ui.Error
import ru.apmgor.presentation.common.ui.Loading
import ru.apmgor.presentation.common.ui.UiState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addForecastUseCase: AddForecastUseCase,
    private val getCityUseCase: GetCityUseCase,
    getForecastUseCase: GetForecastUseCase,
    getUnitsUseCase: GetUnitsUseCase
) : ViewModel() {

    var alertsButtonString by mutableStateOf("")
        private set

    private val _mainState = MutableSharedFlow<UiState<Forecast>>()
    val mainState = getForecastUseCase()
        .filterNotNull()
        .filter { it.city?.timezoneOffset != null }
        .onEach {
            alertsButtonString = getAlertsString(it.alertsForecast)
        }
        .map { Content(it) as UiState<Forecast> }
        .onStart { emit(Loading) }
        .merge(_mainState)
        .catch { emit(Error(it)) }
        .onEach { println(it) }

    val units = getUnitsUseCase()

    val city = getCityUseCase()

    init {
        viewModelScope.launch {
            getCityUseCase()
                .filterNotNull()
                .filter { it.timezoneOffset == null }
                .collect {
                    addForecast(it)
                }
        }
    }

    suspend fun addForecast(city: City) {
        try {
            _mainState.emit(Loading)
            addForecastUseCase(city)
        } catch (e: Throwable) {
            _mainState.emit(Error(e))
        }
    }


    private fun getAlertsString(alerts: List<AlertsForecast>): String {
        if (alerts.isNotEmpty()) {
            return alerts.first().event + if (alerts.size > 1) " + ${alerts.size - 1}" else ""
        }
        return ""
    }

    private fun <T> Flow<T>.merge(flow: Flow<T>): Flow<T> =
        merge(this, flow)
}