package com.example.nhamngocduc.ui.login_signup.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.nhamngocduc.R

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    password: String,
    submitPasswordCondition: Boolean,
    invalidPasswordText: String,
    onPasswordChanged: (String) -> Unit,
) {
    var isPasswordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    InfoTextSector(
        modifier = modifier,
        isPassword = true,
        isHidden = isPasswordHidden,
        isNotValid = !submitPasswordCondition,
        invalidText = invalidPasswordText,
        imeAction = imeAction,
        value = password,
        placeholder = "Password",
        leadingIconResId = R.drawable.ic_password,
        onValueChanged = onPasswordChanged,
        onTrailingIconClick = {
            isPasswordHidden = !isPasswordHidden
        }
    )
}