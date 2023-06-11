package ru.apmgor.presentation.common.ui

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun IconButton(
    image: ImageVector,
    @StringRes descId: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = image,
            contentDescription = stringResource(id = descId)
        )
    }
}