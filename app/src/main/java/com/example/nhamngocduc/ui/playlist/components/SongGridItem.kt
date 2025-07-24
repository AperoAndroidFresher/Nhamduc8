package com.example.nhamngocduc.ui.playlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.data.model.Song
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.TimeConverter

@Composable
fun SongGridItem(
    modifier: Modifier = Modifier,
    song: Song,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit
) {
    var isDropdownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    painter = painterResource(song.songImage),
                    contentDescription = "Song Image"
                )
                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    IconButton(
                        onClick = {isDropdownMenuVisible = true},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Black.copy(alpha = 0.5f),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .size(32.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(R.drawable.ic_options),
                            contentDescription = ""
                        )
                    }
                    DropDownOptions(
                        isDropdownMenuVisible = isDropdownMenuVisible,
                        dropDownItems = dropDownItems,
                        dpOffset = DpOffset((-80).dp, 0.dp),
                        onDropDownMenuVisibilityChange = { isDropdownMenuVisible = false },
                        onOptionSelected = { dropDownOption ->
                            onOptionSelected(dropDownOption, song)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .basicMarquee(),
                text = song.songName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = song.artistName,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                ),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = TimeConverter.toSongDuration(song.songDuration),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            )
        }
    }
}

