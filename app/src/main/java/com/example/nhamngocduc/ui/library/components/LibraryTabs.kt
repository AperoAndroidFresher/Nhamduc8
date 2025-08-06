package com.example.nhamngocduc.ui.library.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.ui.components.button.ScaledTextButton
import com.example.nhamngocduc.util.Tab

@Composable
fun LibraryTabs(
    modifier: Modifier = Modifier,
    currentTab: Tab,
    onTabSelected: (Tab) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.spacedBy(32.dp)
    ) {
        Tab(
            modifier = Modifier.padding(start = 16.dp).weight(1f),
            label = "Local",
            isSelected = currentTab == Tab.LOCAL,
            onClick = {
                onTabSelected(Tab.LOCAL)
            }
        )
        Tab(
            modifier = Modifier.padding(end = 16.dp).weight(1f),
            label = "Remote",
            isSelected = currentTab == Tab.REMOTE,
            onClick = {
                onTabSelected(Tab.REMOTE)
            }
        )
    }
}

@Composable
fun Tab(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    ScaledTextButton(
        modifier = modifier,
        label = label,
        shape = RoundedCornerShape(percent = 25),
        showRipple = !isSelected,
        color = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondaryContainer
        },
        onClick = onClick
    )
}