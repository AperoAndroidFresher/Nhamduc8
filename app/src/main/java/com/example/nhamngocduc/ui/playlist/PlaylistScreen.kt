package com.example.nhamngocduc.ui.playlist

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.ui.playlist.components.PlayListBody
import com.example.nhamngocduc.ui.playlist.components.PlaylistTopBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel,
) {

    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            Manifest.permission.READ_MEDIA_AUDIO
        )
    } else {
        rememberPermissionState(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            viewModel.processIntent(PlaylistContract.Intent.GrantPermission)
        }
    }

    Column(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {
        PlaylistTopBar(
            state = state.value,
            onViewModeClick = {
                viewModel.processIntent(PlaylistContract.Intent.ToggleViewMode)
            },
            onSortClick = {
                viewModel.processIntent(PlaylistContract.Intent.ToggleSort)
            }
        )
        PlayListBody(
            modifier = Modifier.fillMaxHeight(),
            state = state.value,
            onOptionSelected = { option, song ->
                viewModel.processIntent(PlaylistContract.Intent.SelectDropDownOption(option, song))
            }
        )
    }
}

