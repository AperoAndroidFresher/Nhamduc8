package com.example.nhamngocduc.ui.components.lazy_item

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.ui.components.DropDownOptions
import com.example.nhamngocduc.ui.components.animation.animateOnPressAndTap
import com.example.nhamngocduc.ui.components.button.OptionButton
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.TimeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SongListItem(
    modifier: Modifier = Modifier,
    song: Song,
    sortedMode: Boolean = false,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    var isDropdownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

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

    Card(
        modifier = modifier
            .graphicsLayer(scale, scale)
            .animateOnPressAndTap(
                onClick = {},
                onPressStateChange = { isPressed = it }
            ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = ImageRequest.Builder(context)
                    .data(albumArtBitmap ?: R.drawable.folk_song)
                    .crossfade(true)
                    .error(R.drawable.folk_song)
                    .build(),
                contentDescription = song.title,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                )
            }
            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = TimeConverter.toSongDuration(song.duration),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Light
                )
            )
            Spacer(modifier = Modifier.width(8.dp))

            Box {
                OptionButton(
                    modifier = Modifier.fillMaxHeight(),
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
