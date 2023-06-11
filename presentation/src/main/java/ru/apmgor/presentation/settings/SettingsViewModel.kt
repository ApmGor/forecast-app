package ru.apmgor.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.apmgor.domain.model.Units
import ru.apmgor.domain.usecases.AddUnitsUseCase
import ru.apmgor.domain.usecases.GetUnitsUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val addUnitsUseCase: AddUnitsUseCase,
    getUnitsUseCase: GetUnitsUseCase
) : ViewModel() {

    val units = getUnitsUseCase()

    inline fun <reified T> addUnits(
        selected: String,
        unitsNames: Array<String>,
        units: List<Units>
    ) {
        viewModelScope.launch {
            val index = unitsNames.indexOf(selected)
            val list = units.map {
                if (it is T) {
                    T::class.java.enumConstants?.get(index) as Units
                } else {
                    it
                }
            }
            addUnitsUseCase(list)
        }
    }
}