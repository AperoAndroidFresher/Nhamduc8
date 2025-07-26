package com.example.nhamngocduc.ui.login_signup.login.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RememberAndForgotSection(
    modifier: Modifier = Modifier,
    rememberMe: Boolean,
    onRememberMeChanged: (Boolean) -> Unit,
    onForgotClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(32.dp),
            checked = rememberMe,
            onCheckedChange = onRememberMeChanged,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.inversePrimary,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Text(
            modifier = Modifier.weight(1f),
            text = " Remember me",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
        TextButton(
            onClick = onForgotClick
        ) {
            Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}