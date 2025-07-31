package com.example.nhamngocduc.ui.playlist.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.ui.components.OptionButton
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.TimeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SongGridItem(
    modifier: Modifier = Modifier,
    song: Song,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var isDropdownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var albumArtBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(song.contentUri) {
        albumArtBitmap = null

        if (song.contentUri == Uri.EMPTY) {
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
                AsyncImage(
                    modifier = Modifier
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

