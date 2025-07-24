package com.example.nhamngocduc.ui.playlist.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.data.model.Song
import com.example.nhamngocduc.util.TimeConverter

@Composable
fun SongListItem(
    modifier: Modifier = Modifier,
    song: Song,
    dropDownItems: List<String>,
    onDeleteItem: (Int) -> Unit
) {
    var isDropdownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier,
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp)),
                painter = painterResource(song.songImage),
                contentDescription = "Song Image"
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.songName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artistName,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                )
            }
            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = TimeConverter.toSongDuration(song.songDuration),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            )
            Box {
                IconButton(
                    onClick = { isDropdownMenuVisible = true }
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_options),
                        contentDescription = ""
                    )
                }
                DropdownMenu(
                    expanded = isDropdownMenuVisible,
                    onDismissRequest = {
                        isDropdownMenuVisible = false
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    containerColor = Color.Black
                ) {
                    dropDownItems.forEach {
                        DropdownMenuItem(
                            onClick = {
                                isDropdownMenuVisible = false
                                onDeleteItem(song.id)
                            },
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = it,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = Color.White
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
