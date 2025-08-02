package com.example.nhamngocduc.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.ui.playlist.components.PlayListItem

@Composable
fun PlaylistDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    visibleDialogContent: Boolean,
    playlists: List<Playlist>,
    onDismissDialog: () -> Unit,
    onAddClick: () -> Unit,
) {
    if(showDialog) {
        Dialog(
            onDismissRequest = onDismissDialog
        ) {
            AnimatedVisibility(
                visible = visibleDialogContent,
                enter =  fadeIn(tween(600)) + slideInVertically(
                    initialOffsetY = {it},
                    animationSpec = tween(500)
                ) ,
                exit = slideOutVertically(
                    targetOffsetY = {it},
                    animationSpec = tween(500)
                ) + fadeOut(tween(500))
            ) {
                Surface(
                    modifier = modifier,
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Choose playlist",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (playlists.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "You don't have any playlists. Click the \"+\" button to add",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                ScaledIconButton(
                                    modifier = Modifier.size(128.dp),
                                    resId = R.drawable.ic_big_add,
                                    size = 128.dp,
                                    buttonColor = Color.Transparent,
                                    iconColor = MaterialTheme.colorScheme.onSurface,
                                    onClick = onAddClick
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = playlists,
                                    key = {playlist -> playlist.id}
                                ) { playlist ->
                                    PlayListItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        playlist = playlist
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}