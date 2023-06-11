package ru.apmgor.presentation.common.ui

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.apmgor.data.R

@Composable
fun PermissionDialog(
    launcher: Launcher,
    modifier: Modifier = Modifier,
    openDialogChange: (Boolean) -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            openDialogChange(false)
        },
        icon = { Icon(Icons.Filled.Place, contentDescription = null) },
        title = {
            Text(text = stringResource(R.string.title_dialog))
        },
        text = {
            Text(stringResource(R.string.text_dialog))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialogChange(false)
                    launchReqPermission(launcher)
                }
            ) {
                Text(stringResource(R.string.confirm_dialog))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialogChange(false)
                }
            ) {
                Text(stringResource(R.string.dismiss_dialog))
            }
        }
    )
}

typealias Launcher =
        ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>

fun launchReqPermission(launcher: Launcher) {
    launcher.launch(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
}
