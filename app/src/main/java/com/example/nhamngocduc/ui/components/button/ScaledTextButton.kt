package com.example.nhamngocduc.ui.components.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.ui.components.scaleOnPress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaledTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    shape: Shape,
    showRipple: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val buttonContent = @Composable {
        TextButton(
            modifier = modifier.scaleOnPress(interactionSource),
            enabled = enabled,
            interactionSource = interactionSource,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = MaterialTheme.colorScheme.background,
                containerColor = color,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            onClick = onClick
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    if (showRipple) {
        buttonContent()
    } else {
        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            buttonContent()
        }
    }
}