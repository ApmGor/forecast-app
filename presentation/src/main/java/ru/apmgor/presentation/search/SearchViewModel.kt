package ru.apmgor.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.apmgor.domain.model.City
import ru.apmgor.domain.usecases.AddCityUseCase
import ru.apmgor.domain.usecases.GetCitiesUseCase
import ru.apmgor.domain.usecases.GetCityUseCase
import ru.apmgor.presentation.common.ui.Content
import ru.apmgor.presentation.common.ui.Error
import ru.apmgor.presentation.common.ui.Loading
import ru.apmgor.presentation.common.ui.NoContent
import ru.apmgor.presentation.common.ui.UiState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val addCityUseCase: AddCityUseCase,
    getCityUseCase: GetCityUseCase
) : ViewModel() {

    private val _citiesState = MutableStateFlow<UiState<List<City>>>(NoContent)
    val citiesState = _citiesState.asStateFlow()

    val city = getCityUseCase()

    var needCleared by mutableStateOf(false)
        private set

    fun getCities(citiesName: String) {
        _citiesState.value = Loading
        viewModelScope.launch {
            _citiesState.value = try {
                Content(getCitiesUseCase(citiesName))
            } catch (e: Throwable) {
                Error(e)
            }
        }
    }

    fun clearSearch() {
        _citiesState.value = NoContent
        needCleared = false
    }

    fun addCity(city: City?) {
        _citiesState.value = Loading
        viewModelScope.launch {
            try {
                addCityUseCase(city)
                needCleared = true
            } catch (e: Throwable) {
                _citiesState.value = Error(e)
                needCleared = false
            }
        }
    }
}