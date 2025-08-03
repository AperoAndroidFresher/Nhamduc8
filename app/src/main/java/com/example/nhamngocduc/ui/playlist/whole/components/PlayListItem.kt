package com.example.nhamngocduc.ui.playlist.whole.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.ui.components.DropDownOptions
import com.example.nhamngocduc.ui.components.OptionButton
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.playlistWholeDropDownOption

@Composable
fun PlayListItem(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    showDropDownOption: Boolean = false,
    onOptionSelected: (DropDownOption, Playlist) -> Unit = {d, p -> }
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
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                painter = rememberAsyncImagePainter(playlist.image),
                contentDescription = null,
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = playlist.playlistName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${playlist.songsList.size} songs",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                )
            }
            if(showDropDownOption) {
                Box {
                    OptionButton(
                        resId = R.drawable.ic_options,
                        onClick = { isDropdownMenuVisible = true }
                    )
                    DropDownOptions(
                        isDropdownMenuVisible = isDropdownMenuVisible,
                        dropDownItems = playlistWholeDropDownOption,
                        onDropDownMenuVisibilityChange = { isDropdownMenuVisible = false },
                        onOptionSelected = { dropDownOption ->
                            onOptionSelected(dropDownOption, playlist)
                        }
                    )
                }
            }
        }
    }
}