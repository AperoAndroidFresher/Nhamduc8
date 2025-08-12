package com.example.nhamngocduc.ui.components.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.nhamngocduc.ui.components.animation.animateOnPressAndTap

@Composable
fun ScaledPlainTextButton (
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    fontWeight: FontWeight,
    textAlign: TextAlign = TextAlign.Start,
    color: Color,
    onClick: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    Text(
        modifier = modifier
            .graphicsLayer(scale, scale)
            .animateOnPressAndTap(
                onClick = onClick,
                onPressStateChange = { isPressed = it }
            ),
        text = text,
        style = textStyle,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}