package com.example.nhamngocduc.ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.ui.components.animation.scaleOnPress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaledIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes resId: Int,
    size: Dp = 24.dp,
    buttonColor: Color = Color.Transparent,
    iconColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        IconButton(
            modifier = modifier
                .scaleOnPress(interactionSource),
            enabled = enabled,
            interactionSource = interactionSource,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = iconColor,
                containerColor = buttonColor
            ),
            onClick = onClick
        ) {
            AnimatedVisibility(
                visible = enabled,
                enter = scaleIn(tween(500)),
                exit = scaleOut(tween(250))
            ) {
                Icon(
                    modifier = Modifier.size(size),
                    painter = painterResource(resId),
                    contentDescription = "",
                )
            }
        }
    }
}