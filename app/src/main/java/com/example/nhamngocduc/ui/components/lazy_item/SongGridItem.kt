package com.example.nhamngocduc.ui.components.lazy_item

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.ui.components.DropDownOptions
import com.example.nhamngocduc.ui.components.animation.animateOnPressAndTap
import com.example.nhamngocduc.ui.components.button.OptionButton
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.TimeConverter

@Composable
fun SongGridItem(
    modifier: Modifier = Modifier,
    song: Song,
    isSelected: Boolean = false,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit,
    onSongClick: (Song) -> Unit,
) {
    var isDropdownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    
    Card(
        modifier = modifier
            .graphicsLayer(scale, scale)
            .animateOnPressAndTap(
                onClick = {
                    onSongClick(song)
                },
                onPressStateChange = { isPressed = it }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
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
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    model = song.contentUri ?: R.drawable.folk_song,
                    contentDescription = song.title,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.folk_song),
                    error = painterResource(R.drawable.folk_song)
                )

                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    OptionButton(
                        modifier = Modifier.size(32.dp),
                        resId = R.drawable.ic_options,
                        buttonColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        onClick = {
                            isDropdownMenuVisible = true
                        }
                    )
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                text = song.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = song.artist,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                ),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = TimeConverter.toSongDuration(song.duration),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.ExtraLight
                )
            )
        }
    }
}

