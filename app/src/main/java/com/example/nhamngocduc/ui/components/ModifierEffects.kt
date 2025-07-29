package com.example.nhamngocduc.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.scaleOnPress(
    interactionSource: MutableInteractionSource,
    scale: Float = 0.87f
): Modifier = composed {
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) scale else 1f,
    )

    this.graphicsLayer(
        scaleX = scale,
        scaleY = scale
    )
}

