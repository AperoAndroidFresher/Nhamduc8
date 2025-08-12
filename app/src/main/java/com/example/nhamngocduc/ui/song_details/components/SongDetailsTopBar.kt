package com.example.nhamngocduc.ui.song_details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.button.ScaledIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongDetailsTopBar(
    modifier: Modifier = Modifier,
    onStopMusicService: () -> Unit,
    onNavigateBackClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Now Playing",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
        navigationIcon = {
            ScaledIconButton(
                resId = R.drawable.ic_back,
                onClick = onNavigateBackClick
            )
        },
        actions = {
            ScaledIconButton(
                resId = R.drawable.ic_close,
                onClick = onStopMusicService
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
    )
}