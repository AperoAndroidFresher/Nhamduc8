package com.example.nhamngocduc.ui.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.ui.components.animation.animateOnPressAndTap

@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    album: AlbumDomain
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    Row(
        modifier = modifier
            .graphicsLayer(scale, scale)
            .animateOnPressAndTap(
                onClick = {  },
                onPressStateChange = { isPressed = it }
            )
            .clip(RoundedCornerShape(8.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Black.copy(0.8f), Color.DarkGray)
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = album.albumImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(
            modifier = Modifier.width(16.dp)
        )
        Column(
            modifier = Modifier.padding(end = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = album.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = album.artistName,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}