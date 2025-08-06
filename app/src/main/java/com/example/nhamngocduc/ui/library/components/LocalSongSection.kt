package com.example.nhamngocduc.ui.library.components

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.DropDownOption
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocalSongSection(
    modifier: Modifier = Modifier,
    songs: List<Song>,
    onOptionSelected: (DropDownOption, Song) -> Unit,
    grantPermission: () -> Unit,
    loadSongs: () -> Unit,
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

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            grantPermission()
            loadSongs()
        }
    }
    LibrarySongsList(
        modifier = modifier,
        songs = songs,
        onOptionSelected = onOptionSelected
    )
}