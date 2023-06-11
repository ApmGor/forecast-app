package ru.apmgor.presentation.alerts

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import ru.apmgor.domain.usecases.GetAlertsUseCase
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    getAlertsUseCase: GetAlertsUseCase
) : ViewModel() {

    val alerts = getAlertsUseCase()
        .filterNotNull()
}