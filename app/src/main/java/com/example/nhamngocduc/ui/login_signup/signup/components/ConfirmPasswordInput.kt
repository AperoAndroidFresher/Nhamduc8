package com.example.nhamngocduc.ui.login_signup.signup.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.login_signup.components.InfoTextSector

@Composable
fun ConfirmPasswordInputSection(
    modifier: Modifier = Modifier,
    confirmPassword: String,
    submitConfirmPasswordMatching: Boolean,
    errorConfirmPasswordText: String,
    onConfirmPasswordChanged: (String) -> Unit
) {
    var isConfirmPasswordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    InfoTextSector(
        modifier = modifier,
        isPassword = true,
        isHidden = isConfirmPasswordHidden,
        isError = !submitConfirmPasswordMatching,
        errorText = errorConfirmPasswordText,
        imeAction = ImeAction.Next,
        value = confirmPassword,
        placeholder = "Confirm password",
        leadingIconResId = R.drawable.ic_password,
        onValueChanged = onConfirmPasswordChanged,
        onTrailingIconClick = {
            isConfirmPasswordHidden = !isConfirmPasswordHidden
        }
    )
}