package com.example.nhamngocduc.ui.login_signup.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.ui.components.ScaledTextButton
import com.example.nhamngocduc.ui.login_signup.components.AppLogo
import com.example.nhamngocduc.ui.login_signup.components.PasswordInput
import com.example.nhamngocduc.ui.login_signup.components.ScreenLabel
import com.example.nhamngocduc.ui.login_signup.components.UsernameInput
import com.example.nhamngocduc.ui.login_signup.login.components.NewAccountSection
import com.example.nhamngocduc.ui.login_signup.login.components.RememberAndForgotSection
import com.example.nhamngocduc.util.Checker

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    val userName = state.username

    val password = state.password

    val rememberMe = state.rememberMe

    val submitUsernameInvalid = state.accountValidation.usernameCondition

    val submitPasswordInvalid = state.accountValidation.passwordCondition

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AppLogo(
            modifier = Modifier.background(color = Color.Transparent),
            size = 192.dp
        )
        ScreenLabel(
            modifier = Modifier.fillMaxWidth(),
            label = "Login to your account"
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Username input
        UsernameInput(
            modifier = Modifier.fillMaxWidth(),
            userName = userName,
            submitUsernameCondition = submitUsernameInvalid.isEmpty(),
            invalidText = submitUsernameInvalid,
            onValueChanged = {
                viewModel.processIntent(LoginContract.Intent.ChangeInput(it, LoginInputType.USERNAME))
            }
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Password input
        PasswordInput(
            modifier = Modifier.fillMaxWidth(),
            imeAction = ImeAction.Done,
            password = password,
            submitPasswordCondition = submitPasswordInvalid.isEmpty(),
            invalidPasswordText = submitPasswordInvalid,
            onPasswordChanged = {
                viewModel.processIntent(LoginContract.Intent.ChangeInput(it, LoginInputType.PASSWORD))
            }
        )

        RememberAndForgotSection(
            modifier = Modifier.fillMaxWidth(),
            rememberMe = rememberMe,
            onRememberMeChanged = {
                viewModel.processIntent(LoginContract.Intent.ToggleRememberMe)
            },
            onForgotClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        ScaledTextButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = Checker.checkInputsNotEmpty(userName, password),
            label = "Log in",
            shape = RoundedCornerShape(percent = 50),
            onClick = onLoginClick
        )
        Spacer(modifier = Modifier.weight(1f))

        NewAccountSection(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            onSignupClick = onSignupClick
        )
    }
}