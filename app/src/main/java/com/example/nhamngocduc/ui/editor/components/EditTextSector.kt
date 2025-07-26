package com.example.nhamngocduc.ui.editor.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun EditTextSector(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isNotValid: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    errorText: String = "",
    invalidText: String = "",
    value: String,
    sectorLabelText: String,
    placeholderText: String,
    enabled: Boolean,
    onValueChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier.padding(4.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = sectorLabelText,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = !enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            isError = isError,
            value = value,
            onValueChange = onValueChanged,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.8f
                        ),
                        fontWeight = FontWeight.Medium
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            supportingText = {
                if (isNotValid) {
                    Text(
                        text = invalidText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Medium
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                if (isError) {
                    Text(
                        text = errorText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Medium
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                } else {
                    Text("")
                }
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.8f
                ),
                disabledTextColor = MaterialTheme.colorScheme.primary,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onTertiary,
                focusedContainerColor = MaterialTheme.colorScheme.onTertiary,
                disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            ),
            shape = MaterialTheme.shapes.medium,
            minLines = minLines,
            maxLines = maxLines
        )
    }
}