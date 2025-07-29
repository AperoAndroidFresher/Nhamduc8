package com.example.nhamngocduc.ui.playlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.ScaledIconButton
import com.example.nhamngocduc.util.ViewMode

@Composable
fun PlaylistTopBar(
    modifier: Modifier = Modifier,
    viewMode: ViewMode,
    sortedMode: Boolean,
    onSortClick: () -> Unit,
    onViewModeClick: () -> Unit
) {
    Row(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScaledIconButton(
            enabled = sortedMode,
            resId = R.drawable.ic_close,
            onClick = {}
        )
        IconButton(
            enabled = false,
            onClick = { }
        ){}
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = if (!sortedMode) "My playlist" else "Sorting",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        )
        ScaledIconButton(
            enabled = !sortedMode,
            resId = when (viewMode) {
                ViewMode.GRID -> R.drawable.ic_list_view
                ViewMode.LIST -> R.drawable.ic_grid_view
            },
            onClick = onViewModeClick,
        )
        ScaledIconButton(
            enabled = viewMode == ViewMode.LIST,
            resId = if (!sortedMode) R.drawable.ic_sort else R.drawable.ic_check_done,
            onClick = onSortClick
        )
    }
}