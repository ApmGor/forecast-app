package ru.apmgor.presentation.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AlertsScreen(
    modifier: Modifier = Modifier,
    viewModel: AlertsViewModel = hiltViewModel()
) {
    val alerts by viewModel.alerts.collectAsStateWithLifecycle(emptyList())

    LazyColumn(modifier = modifier) {
        items(alerts) {
            ListItem(
                headlineContent = { Text(text = it.event) },
                supportingContent = {
                    Column {
                        Text(text = "${it.start} - ${it.end}")
                        Text(text = it.description)
                    }
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}


