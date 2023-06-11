package ru.apmgor.presentation.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.apmgor.data.R
import ru.apmgor.domain.model.DistUnits
import ru.apmgor.domain.model.PrecipitationUnits
import ru.apmgor.domain.model.PressureUnits
import ru.apmgor.domain.model.TempUnits
import ru.apmgor.domain.model.TimeUnits
import ru.apmgor.domain.model.Units
import ru.apmgor.domain.model.WindUnits
import ru.apmgor.presentation.common.theme.ForecastTheme

@Composable
fun SettingsScreen(
    tempUnits: Array<String>,
    windUnits: Array<String>,
    pressureUnits: Array<String>,
    precipitationUnits: Array<String>,
    distUnits: Array<String>,
    timeUnits: Array<String>,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val units by viewModel.units.collectAsStateWithLifecycle(emptyList())

    val desc = stringArrayResource(id = R.array.units_desc)

    Settings(modifier) {
        if (units.isNotEmpty()) {
            SelectableElement(desc[0], tempUnits, units[0]) {
                viewModel.addUnits<TempUnits>(it, tempUnits, units)
            }
            SelectableElement(desc[1], windUnits, units[1]) {
                viewModel.addUnits<WindUnits>(it, windUnits, units)
            }
            SelectableElement(desc[2], pressureUnits, units[2]) {
                viewModel.addUnits<PressureUnits>(it, pressureUnits, units)
            }
            SelectableElement(desc[3], precipitationUnits, units[3]) {
                viewModel.addUnits<PrecipitationUnits>(it, precipitationUnits, units)
            }
            SelectableElement(desc[4], distUnits, units[4]) {
                viewModel.addUnits<DistUnits>(it, distUnits, units)
            }
            SelectableElement(desc[5], timeUnits, units[5]) {
                viewModel.addUnits<TimeUnits>(it, timeUnits, units)
            }
        }
    }
}


@Composable
inline fun Settings(modifier: Modifier = Modifier, body: @Composable () -> Unit) {
    Column(modifier) {
        body()
    }
}

@Composable
inline fun SelectableElement(
    desc: String,
    unitsNames: Array<String>,
    units: Units,
    crossinline onValueChange: (String) -> Unit
) {

    var isVisible by remember { mutableStateOf(false) }
    var selectedOption = unitsNames[units.value]

    if (isVisible) {
        SelectDialog(
            desc = desc,
            units = unitsNames,
            selectedOption = selectedOption,
            onOptionSelected = {
                selectedOption = it
                onValueChange(it)
            },
            onVisibleChange = { isVisible = it }
        )
    }

    ListItem(
        headlineContent = { Text(text = desc) },
        supportingContent = { Text(text = selectedOption) },
        modifier = Modifier.clickable { isVisible = true }
    )
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable
inline fun SelectDialog(
    desc: String,
    units: Array<String>,
    selectedOption: String,
    crossinline onOptionSelected: (String) -> Unit,
    crossinline onVisibleChange: (Boolean) -> Unit
) {
    var selected by remember { mutableStateOf(selectedOption) }

    AlertDialog(
        onDismissRequest = { onVisibleChange(false) },
        title = { Text(text = desc) },
        text = {
            RadioGroup(
                radioOptions = units,
                selectedOption = selected,
                onOptionSelected = { selected = it }
            )
        },
        confirmButton = {
            CustomTextButton(buttonTextId = R.string.button_text_ok) {
                onOptionSelected(selected)
                onVisibleChange(false)
            }
        },
        dismissButton = {
            CustomTextButton(buttonTextId = R.string.button_text_chancel) {
                onVisibleChange(false)
            }
        }
    )
}

@Composable
inline fun CustomTextButton(@StringRes buttonTextId: Int, crossinline onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text(stringResource(buttonTextId))
    }
}

@Composable
inline fun RadioGroup(
    radioOptions: Array<String>,
    selectedOption: String,
    crossinline onOptionSelected: (String) -> Unit
) {
    Column(Modifier.selectableGroup()) {
        Divider()
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Divider()
    }
}



