package com.example.nhamngocduc.ui.now_playing

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.button.ScaledIconButton
import com.example.nhamngocduc.ui.components.button.ScaledPlainIconButton
import com.example.nhamngocduc.util.TimeConverter

@Composable
fun NowPlayingScreen(
    modifier: Modifier = Modifier,
    playbackState: PlaybackState,
    onPlayOrPauseClick: () -> Unit,
    onSkipPreviousClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    onSeek: (Long) -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit
) {
    val song = playbackState.currentSong ?: return

    val isPlaying = playbackState.isPlaying
    val currentDuration = playbackState.currentPosition
    val totalDuration = playbackState.totalDuration
    val isShuffled = playbackState.isShuffled
    val isRepeated = playbackState.isRepeated

    val progress = if (totalDuration > 0) currentDuration.toFloat() / totalDuration.toFloat() else 0f

    var isSeeking by remember { mutableStateOf(false) }
    var tempProgress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(progress) {
        if (!isSeeking) {
            tempProgress = progress
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            model = song.contentUri ?: R.drawable.folk_song,
            contentDescription = song.title,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.folk_song),
            error = painterResource(R.drawable.folk_song)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.basicMarquee(),
            text = song.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = song.artist,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Slider for seek
        Slider(
            value = tempProgress,
            onValueChange = {
                tempProgress = it
                isSeeking = true
            },
            onValueChangeFinished = {
                onSeek((tempProgress * totalDuration).toLong())
                isSeeking = false
            },
            valueRange = 0f..1f,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = TimeConverter.toSongDuration(currentDuration),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = TimeConverter.toSongDuration(totalDuration),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Shuffle
            ScaledPlainIconButton(
                modifier = Modifier.size(24.dp),
                resId = R.drawable.ic_shuffle,
                color = if (isShuffled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                onClick = onShuffleClick
            )

            // Previous
            ScaledPlainIconButton(
                modifier = Modifier.size(32.dp),
                resId = R.drawable.ic_skip_previous,
                onClick = onSkipPreviousClick
            )

            // Play/Pause
            ScaledIconButton(
                modifier = Modifier.size(64.dp),
                size = 32.dp,
                buttonColor = MaterialTheme.colorScheme.primary,
                resId = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
                onClick = onPlayOrPauseClick
            )

            // Next
            ScaledPlainIconButton(
                modifier = Modifier.size(32.dp),
                resId = R.drawable.ic_skip_next,
                onClick = onSkipNextClick
            )

            // Repeat
            ScaledPlainIconButton(
                modifier = Modifier.size(24.dp),
                resId = R.drawable.ic_repeat,
                color = if (isRepeated) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                onClick = onRepeatClick
            )
        }
    }
}
