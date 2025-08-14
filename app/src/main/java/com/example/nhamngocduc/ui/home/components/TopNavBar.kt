package com.example.nhamngocduc.ui.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.animation.animateOnPressAndTap

@Composable
fun TopNavBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBack: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .graphicsLayer(scale, scale)
                .animateOnPressAndTap(
                    onClick = onClickBack,
                    onPressStateChange = { isPressed = it }
            ),
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(32.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}