package com.example.nhamngocduc.ui.playlist.whole.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.nhamngocduc.ui.components.button.ScaledPlainTextButton

@Composable
fun PlaylistDialog(
    modifier: Modifier = Modifier,
    renameDialog: Boolean = false,
    showDialog: Boolean,
    playlistName: String = "",
    onDismissRequest: () -> Unit,
    onPlaylistAction: (String) -> Unit
) {
    if(showDialog) {
        var playlistName by rememberSaveable {
            mutableStateOf(if (renameDialog) playlistName else "" )
        }

        Dialog(
            onDismissRequest = {
                onDismissRequest()
            }
        ) {
            Surface(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (renameDialog) "Rename Playlist" else "New Playlist",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                        value = playlistName,
                        onValueChange = { playlistName = it },
                        placeholder = {
                            Text(
                                text = if (renameDialog) "" else "Give your playlist a name",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        textStyle = TextStyle(MaterialTheme.colorScheme.primary),
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ScaledPlainTextButton(
                            modifier = Modifier.weight(1f),
                            text = "Cancel",
                            textStyle = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            onClick = {
                                onDismissRequest()
                            }
                        )
                        VerticalDivider(
                            modifier = Modifier.fillMaxHeight(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surface
                        )
                        ScaledPlainTextButton(
                            modifier = Modifier.weight(1f),
                            text = if (renameDialog) "Rename" else "Create",
                            textStyle = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            onClick = {
                                onPlaylistAction(playlistName)
                            }
                        )
                    }
                }
            }
        }
    }
}