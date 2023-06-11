package ru.apmgor.presentation.common.ui

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun CompositionLocalMidM3(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalContentColor
                provides
                MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        content()
    }
}