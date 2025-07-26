package com.example.nhamngocduc.ui.login_signup.signup.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.login_signup.components.InfoTextSector

@Composable
fun PasswordInput(
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
    var isPasswordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    var isConfirmPasswordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    InfoTextSector(
        modifier = modifier,
        isPassword = true,
        isHidden = isPasswordHidden,
        isNotValid = !submitPasswordCondition,
        invalidText = invalidPasswordText,
        imeAction = ImeAction.Next,
        value = password,
        placeholder = "Password",
        leadingIconResId = R.drawable.ic_password,
        onValueChanged = onPasswordChanged,
        onTrailingIconClick = {
            isPasswordHidden = !isPasswordHidden
        }
    )
    Spacer(modifier = Modifier.height(12.dp))

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