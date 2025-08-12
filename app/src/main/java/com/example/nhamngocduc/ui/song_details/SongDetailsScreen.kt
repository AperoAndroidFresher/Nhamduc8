package com.example.nhamngocduc.ui.song_details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.song_details.components.SongDetailsTopBar
import com.example.nhamngocduc.util.TimeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun SongDetailsScreen(
    modifier: Modifier = Modifier,
    songId: Long,
    playlistId: Long?,
    viewModel: SongDetailsViewModel = koinViewModel(),
    onNavigateBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            SongDetailsTopBar(
                modifier = Modifier.fillMaxWidth(),
                onStopMusicService = {},
                onNavigateBackClick = {}
            )
        }
    ) { paddingsValue ->
        SongDetailsBody(
            modifier = Modifier
                .padding(paddingsValue)
                .fillMaxSize(),
            state = state,
            onProgressChanged = { TODO() },
            onPlayOrPauseClick = { TODO() },
            onSkipPreviousClick = { TODO() },
            onSkipNextClick = { TODO() },
            onShuffleClick = { TODO() },
            onRepeatClick = { TODO() },
        )
    }

}

@Composable
fun SongDetailsBody(
    modifier: Modifier = Modifier,
    state: SongDetailsContract.State,
    onProgressChanged: (Float) -> Unit,
    onPlayOrPauseClick: () -> Unit,
    onSkipPreviousClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit,
) {
    val song = state.playingSong!!

    val progress = state.songProgress

    val currentDuration = TimeConverter.toSongDuration(state.currentDuration)

    val songDuration = TimeConverter.toSongDuration(state.totalDuration)

    val isShuffled = state.isShuffled

    val isPlaying = state.isPlaying

    val repeatTintIcon = when (state.repeatMode) {
        RepeatMode.OFF -> MaterialTheme.colorScheme.secondary
        RepeatMode.REPEAT_ONE -> MaterialTheme.colorScheme.primary
        RepeatMode.REPEAT_MULTIPLE -> MaterialTheme.colorScheme.onBackground
    }

    var isSeeking by rememberSaveable { mutableStateOf(false) }

    var tempProgressPosition by rememberSaveable { mutableFloatStateOf(0f) }

    LaunchedEffect(progress) {
        if (!isSeeking) {
            tempProgressPosition = progress
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current

        val coroutineScope = rememberCoroutineScope()

        var albumArtBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }

        LaunchedEffect(song.contentUri) {
            albumArtBitmap = null

            if (song.contentUri == Uri.EMPTY || song.contentUri == null) {
                albumArtBitmap = null
                return@LaunchedEffect
            }

            coroutineScope.launch(Dispatchers.IO) {
                var bitmap: Bitmap? = null
                val retriever = MediaMetadataRetriever()
                try {
                    retriever.setDataSource(context, song.contentUri)
                    val embeddedPicture = retriever.embeddedPicture
                    if (embeddedPicture != null) {
                        bitmap = BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.size)
                    }
                } finally {
                    retriever.release()
                }
                withContext(Dispatchers.Default) {
                    albumArtBitmap = bitmap
                }
            }
        }

        AsyncImage(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
            model = ImageRequest.Builder(context)
                .data(albumArtBitmap ?: R.drawable.folk_song)
                .crossfade(true)
                .error(R.drawable.folk_song)
                .build(),
            contentDescription = song.title,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = song.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = song.artist,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = tempProgressPosition,
            onValueChange = {
                onProgressChanged(it)
                isSeeking = true
            },
            onValueChangeFinished = {
                onProgressChanged(tempProgressPosition)
                isSeeking = false
            },
            valueRange = 0f..100f,
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
                text = currentDuration,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Text(
                text = songDuration,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_shuffle),
                contentDescription = "Shuffle",
                tint = if (!isShuffled)
                    Color.Gray else Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onShuffleClick() }
            )

            Icon(
                painter = painterResource(R.drawable.ic_skip_previous),
                contentDescription = "Skip previous",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onSkipPreviousClick() }
            )

            Icon(
                painter = painterResource(
                    if (!isPlaying) R.drawable.ic_play else R.drawable.ic_pause
                ),
                contentDescription = "Play",
                tint = Color(0xFFBB86FC),
                modifier = Modifier
                    .size(64.dp)
                    .clickable { onPlayOrPauseClick() }
            )

            Icon(
                painter = painterResource(R.drawable.ic_skip_next),
                contentDescription = "Skip next",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onSkipNextClick() }
            )

            Icon(
                painter = painterResource(R.drawable.ic_repeat),
                contentDescription = "Repeat",
                tint = repeatTintIcon,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onRepeatClick() }
            )
        }

    }
}



