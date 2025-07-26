package com.example.nhamngocduc.ui.login_signup.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.nhamngocduc.R

@Composable
fun UsernameInput(
    modifier: Modifier = Modifier,
    userName: String,
    imeAction: ImeAction = ImeAction.Next,
    submitUsernameCondition: Boolean,
    invalidText: String,
    onValueChanged: (String) -> Unit
) {
    InfoTextSector(
        modifier = modifier,
        isNotValid = !submitUsernameCondition,
        invalidText = invalidText,
        imeAction = imeAction,
        value = userName,
        placeholder = "Username",
        leadingIconResId = R.drawable.ic_user,
        onValueChanged = onValueChanged,
    )
}