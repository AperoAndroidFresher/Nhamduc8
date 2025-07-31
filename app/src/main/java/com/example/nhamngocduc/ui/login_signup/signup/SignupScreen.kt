package com.example.nhamngocduc.ui.login_signup.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.ui.components.ScaledTextButton
import com.example.nhamngocduc.ui.login_signup.components.AppLogo
import com.example.nhamngocduc.ui.login_signup.components.ScreenLabel
import com.example.nhamngocduc.ui.login_signup.signup.components.EmailInput
import com.example.nhamngocduc.ui.login_signup.signup.components.PasswordInputSection
import com.example.nhamngocduc.ui.login_signup.components.UsernameInput
import com.example.nhamngocduc.util.Checker
import com.example.nhamngocduc.util.Users.usersMap

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel,
    onSignUp: () -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    val userName = state.username

    val password = state.password

    val confirmPassword = state.confirmPassword

    val email = state.email

    val submitPasswordInvalid = state.accountValidation.passwordCondition

    val submitConfirmPasswordError = state.accountValidation.confirmPasswordCondition

    val submitUsernameInvalid = state.accountValidation.usernameCondition

    val submitEmailInvalid = state.accountValidation.emailCondition

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
            label = "Sign up"
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Username
        UsernameInput(
            modifier = Modifier.fillMaxWidth(),
            userName = userName,
            submitUsernameCondition = submitUsernameInvalid.isEmpty(),
            invalidText = submitUsernameInvalid
        ) {
            viewModel.processIntent(SignupContract.Intent.ChangeInput(it, SignupInputType.USERNAME))
        }
        Spacer(modifier = Modifier.height(12.dp))

        //Password + Confirm password
        PasswordInputSection(
            modifier = Modifier.fillMaxWidth(),
            password = password,
            confirmPassword = confirmPassword,
            submitPasswordCondition = submitPasswordInvalid.isEmpty(),
            submitConfirmPasswordMatching = submitConfirmPasswordError.isEmpty(),
            invalidPasswordText = submitPasswordInvalid,
            errorConfirmPasswordText = submitConfirmPasswordError,
            onPasswordChanged = {
                viewModel.processIntent(SignupContract.Intent.ChangeInput(it, SignupInputType.PASSWORD))
            },
            onConfirmPasswordChanged = {
                viewModel.processIntent(SignupContract.Intent.ChangeInput(it, SignupInputType.CONFIRM_PASSWORD))
            }
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Email
        EmailInput(
            modifier = Modifier.fillMaxWidth(),
            email = email,
            submitEmailCondition = submitEmailInvalid.isEmpty(),
            invalidText = submitEmailInvalid,
            onValueChanged = {
                viewModel.processIntent(SignupContract.Intent.ChangeInput(it, SignupInputType.EMAIL))
            }
        )
        Spacer(modifier = Modifier.weight(1f))

        ScaledTextButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = Checker.checkInputsNotEmpty(userName, password, confirmPassword, email),
            label = "Sign up",
            shape = RoundedCornerShape(percent = 50)
        ) {
            viewModel.processIntent(SignupContract.Intent.SignUp(onSignUp))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
