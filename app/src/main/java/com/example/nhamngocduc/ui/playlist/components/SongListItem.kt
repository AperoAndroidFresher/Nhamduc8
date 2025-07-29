package com.example.nhamngocduc.ui.playlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.data.model.Song
import com.example.nhamngocduc.ui.components.OptionButton
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.TimeConverter

@Composable
fun SongListItem(
    modifier: Modifier = Modifier,
    song: Song,
    sortedMode: Boolean,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit
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
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artistName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                )
            }
            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = TimeConverter.toSongDuration(song.songDuration),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Light
                )
            )
            Box {
                OptionButton(
                    enabled = !sortedMode,
                    resId = if (!sortedMode) {
                        R.drawable.ic_options
                    } else {
                        R.drawable.three_sticks
                    },
                    onClick = { isDropdownMenuVisible = true }
                )
                DropDownOptions(
                    isDropdownMenuVisible = isDropdownMenuVisible,
                    dropDownItems = dropDownItems,
                    onDropDownMenuVisibilityChange = { isDropdownMenuVisible = false },
                    onOptionSelected = { dropDownOption ->
                        onOptionSelected(dropDownOption, song)
                    }
                )
            }
        }
    }
}
