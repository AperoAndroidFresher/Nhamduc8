package com.example.nhamngocduc.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        ScaledTextButton(
            label = "See all",
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.primary,
            onClick = onSeeAllClick
        )
    }
}
