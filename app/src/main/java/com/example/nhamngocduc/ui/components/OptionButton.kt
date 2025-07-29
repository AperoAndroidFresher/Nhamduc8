package com.example.nhamngocduc.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes resId: Int,
    size: Dp = 16.dp,
    iconColor: Color = MaterialTheme.colorScheme.onBackground,
    buttonColor: Color = Color.Transparent,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()

    val targetTintColor = if (isPressed) MaterialTheme.colorScheme.outlineVariant else iconColor

    val animateTintColor by animateColorAsState(
        targetValue = targetTintColor,
    )

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        IconButton(
            modifier = modifier,
            enabled = enabled,
            colors = IconButtonDefaults.iconButtonColors(
                buttonColor,
                animateTintColor
            ),
            interactionSource = interactionSource,
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(size),
                painter = painterResource(resId),
                contentDescription = null,
            )
        }
    }
}