package com.example.nhamngocduc.ui.login_signup.signup.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.ui.login_signup.components.PasswordInput

@Composable
fun PasswordInputSection(
    modifier: Modifier = Modifier,
    password: String,
    confirmPassword: String,
    submitPasswordCondition: Boolean,
    submitConfirmPasswordMatching: Boolean,
    invalidPasswordText: String,
    errorConfirmPasswordText: String,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit
) {
    PasswordInput(
        modifier = modifier,
        password = password,
        submitPasswordCondition = submitPasswordCondition,
        invalidPasswordText = invalidPasswordText,
        onPasswordChanged = onPasswordChanged
    )
    Spacer(modifier = Modifier.height(12.dp))

    ConfirmPasswordInputSection(
        modifier = modifier,
        confirmPassword = confirmPassword,
        submitConfirmPasswordMatching = submitConfirmPasswordMatching,
        errorConfirmPasswordText = errorConfirmPasswordText,
        onConfirmPasswordChanged = onConfirmPasswordChanged
    )
}