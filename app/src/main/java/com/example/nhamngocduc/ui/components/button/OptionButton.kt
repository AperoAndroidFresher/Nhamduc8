package com.example.nhamngocduc.ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.ui.components.animation.animateOnPressAndTap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes resId: Int,
    size: Dp = 20.dp,
    iconColor: Color = MaterialTheme.colorScheme.onBackground,
    buttonColor: Color = Color.Transparent,
    onClick: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }

    val animateTintColor by animateColorAsState(
        targetValue =  if (isPressed) MaterialTheme.colorScheme.outlineVariant else iconColor,
    )

    val animateScale by animateFloatAsState(if (isPressed) 0.85f else 1f)

    Box(
        modifier = modifier
            .graphicsLayer(animateScale, animateScale)
            .background(buttonColor)
            .animateOnPressAndTap(
                onClick = onClick,
                onPressStateChange = { isPressed = it }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(size),
            painter = painterResource(resId),
            contentDescription = null,
            tint = animateTintColor
        )
    }

}