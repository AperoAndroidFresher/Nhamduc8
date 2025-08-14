package com.example.nhamngocduc.ui.components.animation

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter

fun Modifier.scaleOnPress(
    onClick: () -> Unit,
    scaleValue: Float = 0.87f,
): Modifier = composed {
    var selected by remember { mutableStateOf(false) }

    val scale = animateFloatAsState(if (selected) scaleValue else 1f)

    this
        .scale(scale.value)
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    selected = true
                }

                MotionEvent.ACTION_UP -> {
                    selected = false
                    onClick()
                }

                MotionEvent.ACTION_CANCEL -> {
                    selected = false
                }
            }
            true
        }
}

fun Modifier.animateOnPressAndTap(
    onClick: () -> Unit,
    onPressStateChange: (Boolean) -> Unit
) : Modifier = this
    .pointerInput(Unit) {
        detectTapGestures(
            onPress = {
                onPressStateChange(true)
                try {
                    awaitRelease()
                } finally {
                    onPressStateChange(false) // FINALLY NO MORE STUCKKKKKK !!!!!!
                }
            },
            onTap = {
                onClick()
            }
        )
    }

