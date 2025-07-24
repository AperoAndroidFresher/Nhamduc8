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
import com.example.nhamngocduc.util.ViewMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistTopBar(
    modifier: Modifier = Modifier,
    viewMode: ViewMode,
    sortedMode: Boolean,
    onSortClick: () -> Unit,
    onViewModeClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
          Text(
              modifier = Modifier.fillMaxWidth(),
              textAlign = TextAlign.Center,
              text = if (!sortedMode) "My playlist" else "Sorting",
              style = MaterialTheme.typography.headlineSmall.copy(
                  color = Color.White,
                  fontWeight = FontWeight.SemiBold
              )
          )
        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    enabled = sortedMode,
                    onClick = onSortClick
                ) {
                    if (sortedMode) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(
                                R.drawable.ic_close
                            ),
                            contentDescription = "Sort Button",
                            tint = Color.White
                        )
                    }
                }
                IconButton(
                    enabled = false,
                    onClick = { }
                ) {

                }
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    enabled = !sortedMode,
                    onClick = onViewModeClick
                ) {
                    if (!sortedMode) {
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
                }

                IconButton(
                    enabled = viewMode == ViewMode.LIST,
                    onClick = onSortClick
                ) {
                    if (viewMode == ViewMode.LIST) {
                        Icon(
                            modifier = Modifier.size(if (!sortedMode) 24.dp else 20.dp),
                            painter = if (!sortedMode) {
                                painterResource(R.drawable.ic_sort)
                            } else {
                                painterResource(R.drawable.ic_check_done)
                            },
                            contentDescription = "Sort Button",
                            tint = Color.White
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Black),
    )
}