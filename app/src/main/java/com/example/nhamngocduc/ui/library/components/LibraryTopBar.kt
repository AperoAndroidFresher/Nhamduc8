package com.example.nhamngocduc.ui.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.components.ScaledIconButton

@Composable
fun LibraryTopBar(
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ScaledIconButton(
            enabled = false,
            resId = R.drawable.ic_close,
            onClick = {}
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = "Library",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        )
        ScaledIconButton(
            enabled = false,
            resId = R.drawable.ic_close,
            onClick = {}
        )
    }
}