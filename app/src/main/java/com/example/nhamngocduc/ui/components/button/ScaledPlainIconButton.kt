package com.example.nhamngocduc.ui.components.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.example.nhamngocduc.ui.components.animation.animateOnPressAndTap

@Composable
fun ScaledPlainIconButton(
    modifier: Modifier = Modifier,
    resId: Int,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    Icon(
        modifier = modifier
            .graphicsLayer(scale, scale)
            .animateOnPressAndTap(
                onClick = onClick,
                onPressStateChange = { isPressed = it }
            ),
        painter = painterResource(resId),
        contentDescription = null,
        tint = color
    )
}