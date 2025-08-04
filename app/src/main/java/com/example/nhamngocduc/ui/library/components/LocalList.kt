package com.example.nhamngocduc.ui.library.components

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.ui.components.SongListItem
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.libraryDropDownOptions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocalList(
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

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = songs,
            key = {song -> song.songId}
        ) { song ->
            SongListItem(
                modifier = Modifier,
                song = song,
                dropDownItems = libraryDropDownOptions,
                onOptionSelected = onOptionSelected,
            )
        }
    }
}