package com.example.nhamngocduc.ui.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.util.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenTopBar(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    isEditable: Boolean,
    onActionClick: () -> Unit,
    onThemeModeClick: () -> Unit
) {
    val rotationAngle by animateFloatAsState(
        targetValue = when(themeMode) {
            ThemeMode.DARK -> 180f
            ThemeMode.LIGHT -> 0f
        }
    )

    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "MY INFORMATION",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
        navigationIcon = {
            TextButton(
                onClick = onThemeModeClick
            ) {
                Icon(
                    modifier = Modifier.size(24.dp).graphicsLayer(rotationZ = rotationAngle),
                    painter = when(themeMode) {
                        ThemeMode.LIGHT -> painterResource(R.drawable.ic_dark_mode)
                        ThemeMode.DARK -> painterResource(R.drawable.ic_light_mode)
                    },
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        actions = {
            IconButton(
                enabled = !isEditable,
                onClick = onActionClick
            ) {
                AnimatedVisibility(
                    visible = !isEditable,
                    enter = scaleIn(animationSpec = tween(500)),
                    exit = scaleOut(animationSpec = tween(500))
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_edit),
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = ""
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
    )
}