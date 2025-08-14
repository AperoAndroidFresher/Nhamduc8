package com.example.nhamngocduc.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.nhamngocduc.ui.components.button.ScaledPlainTextButton
import com.example.nhamngocduc.ui.components.button.ScaledTextButton

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            ),
        )
        ScaledPlainTextButton(
            text = "See all",
            textStyle = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.inversePrimary,
            onClick = onSeeAllClick,
        )
    }
}
