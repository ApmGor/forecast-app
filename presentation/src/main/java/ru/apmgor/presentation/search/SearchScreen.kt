package ru.apmgor.presentation.search

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.apmgor.data.R
import ru.apmgor.domain.model.City
import ru.apmgor.presentation.common.ui.Content
import ru.apmgor.presentation.common.ui.Error
import ru.apmgor.presentation.common.ui.Loading
import ru.apmgor.presentation.common.ui.NoContent
import ru.apmgor.presentation.common.ui.UiState
import ru.apmgor.presentation.common.ui.IconButton
import ru.apmgor.presentation.common.ui.PermissionDialog
import ru.apmgor.presentation.common.ui.SearchIcon
import ru.apmgor.presentation.common.ui.launchReqPermission

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navToSettings: () -> Unit
) {
    val citiesState by viewModel.citiesState.collectAsStateWithLifecycle()
    val city by viewModel.city.collectAsStateWithLifecycle(null)

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val clearSearch = { text = ""; viewModel.clearSearch() }

    if (viewModel.needCleared) { clearSearch(); active = false }

    LaunchedEffect(citiesState) {
        if (citiesState is Error) {
            viewModel.clearSearch()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it.containsValue(true)) {
            addCity(viewModel,null)
        } else {
            getToastMessage(context, context.getString(R.string.permission_denied))
        }
    }

    if (openDialog) {
        PermissionDialog(
            launcher = launcher,
            openDialogChange = { openDialog = it }
        )
    }

    Box(modifier
        .semantics { isContainer = true }
        .zIndex(1f)
        .fillMaxWidth()
    ) {
        SearchBar(
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                viewModel.getCities(it)
                keyboardController?.hide()
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(text = city?.name ?: stringResource(R.string.search_placeholder_default))
            },
            leadingIcon = {
                LeadingIcon(active = active) { active = false }
            },
            trailingIcon = {
                TrailingIcon(
                    text = text,
                    active = active,
                    clearSearch = clearSearch,
                    navToSettings = navToSettings,
                    onClick = {
                        if (isPermGranted(context))
                            launchReqPermission(launcher)
                        else
                            openDialog = true
                    }
                )
            },
            modifier = modifier.align(Alignment.TopCenter)
        ) {
            citiesState.SearchList(context, { addCity(viewModel, it) })
        }
    }
}

private fun addCity(viewModel: SearchViewModel, city: City?) {
    viewModel.addCity(city)
}

@Composable
private fun TrailingIcon(
    text: String,
    active: Boolean,
    clearSearch: () -> Unit,
    navToSettings: () -> Unit,
    onClick: () -> Unit
) {
    if (!active) {
        IconButton(
            image = Icons.Default.Settings,
            descId = R.string.settings_desc,
            onClick = navToSettings
        )
    } else {
        Row {
            if (text.isNotEmpty()) {
                IconButton(
                    image = Icons.Default.Clear,
                    descId = R.string.clear_desc,
                    onClick = clearSearch
                )
            }
            IconButton(
                image = Icons.Default.Place,
                descId = R.string.place_desc,
                onClick = onClick
            )
        }

    }
}

@Composable
private fun LeadingIcon(active: Boolean, onClick: () -> Unit) {
    if (!active) {
        SearchIcon()
    } else {
        IconButton(
            image = Icons.Default.ArrowBack,
            descId = R.string.back_arrow_desc,
            onClick = onClick
        )
    }
}

@Composable
private fun UiState<List<City>>.SearchList(context: Context, addCity: (City?) -> Unit) {
    when(this) {
        NoContent -> {}
        is Loading -> Loading()
        is Content -> {
            LazyColumn {
                items(this@SearchList.value) {
                    ListItem(
                        headlineContent = {
                            Text(text = buildStringFromCity(it))
                        },
                        leadingContent = { SearchIcon() },
                        modifier = Modifier.clickable { addCity(it) }
                    )
                }
            }
        }
        is Error -> {
            getToastMessage(context, error.localizedMessage)
        }
    }
}

private fun buildStringFromCity(city: City) = buildString {
    append(city.name)
    append(", ")
    if (city.state != null) {
        append(city.state)
        append(", ")
    }
    append(city.country)
}

private fun isPermGranted(context: Context) =
    ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED ||
    ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

fun getToastMessage(context: Context, msg: String?) {
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_LONG
    ).show()
}