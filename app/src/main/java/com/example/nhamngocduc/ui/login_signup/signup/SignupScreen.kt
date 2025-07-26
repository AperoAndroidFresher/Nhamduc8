package com.example.nhamngocduc.ui.login_signup.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.data.model.User
import com.example.nhamngocduc.ui.login_signup.components.AppLogo
import com.example.nhamngocduc.ui.login_signup.components.MainButton
import com.example.nhamngocduc.ui.login_signup.components.ScreenLabel
import com.example.nhamngocduc.ui.login_signup.components.TopBar
import com.example.nhamngocduc.ui.login_signup.signup.components.EmailInput
import com.example.nhamngocduc.ui.login_signup.signup.components.PasswordInput
import com.example.nhamngocduc.ui.login_signup.components.UsernameInput
import com.example.nhamngocduc.util.Checker
import com.example.nhamngocduc.util.Users.usersMap

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onSignUp: () -> Unit
) {
    var userName by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }

    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var email by rememberSaveable { mutableStateOf("") }

    var submitPasswordInvalid by rememberSaveable { mutableStateOf("") }

    var submitConfirmPasswordError by rememberSaveable { mutableStateOf("") }

    var submitUsernameInvalid by rememberSaveable { mutableStateOf("") }

    var submitEmailInvalid by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                }) }
            .background(
            color = MaterialTheme.colorScheme.background
        )
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TopBar(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth(),
                onBackClick = onNavigateBack
            )
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
                userName = it
                submitUsernameInvalid = ""
            }
            Spacer(modifier = Modifier.height(12.dp))

            //Password + Confirm password
            PasswordInput(
                modifier = Modifier.fillMaxWidth(),
                password = password,
                confirmPassword = confirmPassword,
                submitPasswordCondition = submitPasswordInvalid.isEmpty(),
                submitConfirmPasswordMatching = submitConfirmPasswordError.isEmpty(),
                invalidPasswordText = submitPasswordInvalid,
                errorConfirmPasswordText = submitConfirmPasswordError,
                onPasswordChanged = {
                    password = it
                    submitPasswordInvalid = ""
                    submitConfirmPasswordError = ""
                },
                onConfirmPasswordChanged = {
                    confirmPassword = it
                    submitConfirmPasswordError = ""
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
                    email = it
                    submitEmailInvalid = ""
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            MainButton(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth(),
                label = "Sign up"
            ) {
                onSubmitClick(
                    userName = userName,
                    password = password,
                    confirmPassword = confirmPassword,
                    email = email,
                    onUsernameConditionChanged = {
                        submitUsernameInvalid = it
                    },
                    onPasswordConditionChanged = {
                        submitPasswordInvalid = it
                    },
                    onConfirmPasswordMatchingChanged = {
                        submitConfirmPasswordError = it
                    },
                    onEmailConditionChanged = {
                        submitEmailInvalid = it
                    },
                    onSignUpSuccess = {
                        onSignUp()
                    },
                    onClear = {
                        userName = ""
                        password = ""
                        confirmPassword = ""
                        email = ""
                    }
                )
            }
        }
    }
}

private fun onSubmitClick(
    userName: String,
    password: String,
    confirmPassword: String,
    email: String,
    onUsernameConditionChanged: (String) -> Unit,
    onPasswordConditionChanged: (String) -> Unit,
    onConfirmPasswordMatchingChanged: (String) -> Unit,
    onEmailConditionChanged: (String) -> Unit,
    onSignUpSuccess: () -> Unit,
    onClear: () -> Unit
) {
    onUsernameConditionChanged("")
    onPasswordConditionChanged("")
    onConfirmPasswordMatchingChanged("")
    onEmailConditionChanged("")

    val isUsernameValid = Checker.checkUsername(userName)

    val isPasswordValid = Checker.checkPassword(password)

    val isConfirmPasswordValid = Checker.checkPassword(confirmPassword)

    val arePasswordsMatching = Checker.checkConfirmPassword(confirmPassword, password)

    val isEmailNotValid = !Checker.checkEmail(email) && !Checker.checkEmailTail(email)

    if (!isUsernameValid) {
        onUsernameConditionChanged("Invalid format")
    }

    if (!isPasswordValid) {
        onPasswordConditionChanged("Invalid format")
    }

    if (!arePasswordsMatching) {
        onConfirmPasswordMatchingChanged("Not matching")
    }

    if (isEmailNotValid) {
        onEmailConditionChanged("Invalid format")
    }

    if (isUsernameValid && isPasswordValid && isConfirmPasswordValid && arePasswordsMatching && !isEmailNotValid) {
        if (!usersMap.containsKey(userName)) {
            usersMap.put(
                userName,
                User(userName, password, email)
            )
            onSignUpSuccess()
        } else {
            onClear()
            onUsernameConditionChanged("Username has existed")
        }
    }
}
