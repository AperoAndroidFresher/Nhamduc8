package com.example.nhamngocduc.ui.login_signup.signup.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.login_signup.components.InfoTextSector

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String,
    submitEmailCondition: Boolean,
    invalidText: String,
    onValueChanged: (String) -> Unit
) {
    InfoTextSector(
        modifier = modifier,
        isNotValid = !submitEmailCondition,
        invalidText = invalidText,
        imeAction = ImeAction.Done,
        value = email,
        placeholder = "Email",
        leadingIconResId = R.drawable.ic_mail,
        onValueChanged = onValueChanged
    )
}