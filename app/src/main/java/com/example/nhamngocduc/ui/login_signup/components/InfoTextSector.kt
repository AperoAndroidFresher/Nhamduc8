package com.example.nhamngocduc.ui.login_signup.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R

@Composable
fun InfoTextSector(
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isHidden: Boolean = false,
    isError: Boolean = false,
    isNotValid: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction,
    errorText: String = "",
    invalidText: String = "",
    value: String,
    placeholder: String,
    @DrawableRes leadingIconResId: Int,
    onValueChanged: (String) -> Unit,
    onTrailingIconClick: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        isError = isError,
        value = value,
        onValueChange = onValueChanged,
        leadingIcon = {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(leadingIconResId),
                contentDescription = ""
            )
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(
                    onClick = onTrailingIconClick
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = if (isHidden) {
                            painterResource(R.drawable.ic_eye_lock)
                        } else {
                            painterResource(R.drawable.ic_eye_open)
                        },
                        contentDescription = ""
                    )
                }
            }
        },
        placeholder = {
            Text(
                modifier = Modifier.background(Color.Transparent),
                text = placeholder,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.secondary,
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
                    minLines = 1
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
                    minLines = 1
                )
            }
        },
        visualTransformation = if (!isPassword) {
            VisualTransformation.None
        } else {
            if (isHidden) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
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
            focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.8f
            ),
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.onTertiary,
            focusedContainerColor = MaterialTheme.colorScheme.onTertiary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
        ),
        shape = MaterialTheme.shapes.medium,
        minLines = 1,
        maxLines = 1
    )

}