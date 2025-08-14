package com.example.nhamngocduc.ui.components.button

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.animation.animateOnPressAndTap

@Composable
fun CoilImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageUri: Uri?
) {
    val context = LocalContext.current

    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    AsyncImage(
        modifier = modifier
            .graphicsLayer(scale, scale)
            .animateOnPressAndTap(
                onClick = onClick,
                onPressStateChange = { isPressed = it }
            ),
        model = ImageRequest.Builder(context)
            .data(imageUri)
            .size(300, 300)
            .crossfade(true)
            .error(R.drawable.model)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}