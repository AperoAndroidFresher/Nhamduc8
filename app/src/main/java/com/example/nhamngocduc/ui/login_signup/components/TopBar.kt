package com.example.nhamngocduc.ui.login_signup.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.scaleOnPress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    topBarState: Boolean,
    onBackClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                IconButton(
                    modifier = Modifier
                        .scaleOnPress(interactionSource),
                    enabled = topBarState,
                    onClick = onBackClick
                ) {
                    AnimatedVisibility(
                        visible = topBarState,
                        enter = scaleIn(tween(500)),
                        exit = scaleOut(tween(250))
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp),
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = ""
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}