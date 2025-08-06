package com.example.nhamngocduc.ui.playlist.playlist_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.button.ScaledIconButton
import com.example.nhamngocduc.ui.playlist.playlist_detail.PlaylistDetailContract
import com.example.nhamngocduc.util.ViewMode

@Composable
fun PlaylistTopBar(
    modifier: Modifier = Modifier,
    state: PlaylistDetailContract.State,
    onSortClick: () -> Unit,
    onViewModeClick: () -> Unit
) {
    val isSorted = state.isSorted
    val viewMode = state.viewMode

    Row(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScaledIconButton(
            enabled = isSorted,
            resId = R.drawable.ic_close,
            onClick = {}
        )
        ScaledIconButton(
            enabled = false,
            resId = R.drawable.ic_close,
            onClick = {}
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = if (!isSorted) "My playlist" else "Sorting",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        )
        ScaledIconButton(
            enabled = !isSorted,
            resId = when (viewMode) {
                ViewMode.GRID -> R.drawable.ic_list_view
                ViewMode.LIST -> R.drawable.ic_grid_view
            },
            onClick = onViewModeClick,
        )
        ScaledIconButton(
            enabled = viewMode == ViewMode.LIST,
            resId = if (!isSorted) R.drawable.ic_sort else R.drawable.ic_check_done,
            onClick = onSortClick
        )
    }
}