package com.example.nhamngocduc.ui.playlist.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.playlist.ViewMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistTopBar(
    modifier: Modifier = Modifier,
    viewMode: ViewMode,
    onSortClick: () -> Unit,
    onViewModeClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
          Text(
              modifier = Modifier.fillMaxWidth(),
              textAlign = TextAlign.Center,
              text = "My playlist",
              style = MaterialTheme.typography.headlineMedium.copy(
                  color = Color.White,
                  fontWeight = FontWeight.Bold
              )
          )
        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    enabled = false,
                    onClick = onViewModeClick
                ) {

                }

                IconButton(
                    enabled = false,
                    onClick = onSortClick
                ) {

                }
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onViewModeClick
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = when(viewMode) {
                            ViewMode.GRID -> painterResource(R.drawable.ic_list_view)
                            ViewMode.LIST -> painterResource(R.drawable.ic_grid_view)
                        },
                        contentDescription = "View Mode Button",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = onSortClick
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_sort),
                        contentDescription = "Sort Button",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Black),
    )
}