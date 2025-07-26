package com.example.nhamngocduc.ui.login_signup.login

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.login_signup.components.AppLogo
import com.example.nhamngocduc.ui.login_signup.components.InfoTextSector
import com.example.nhamngocduc.ui.login_signup.components.MainButton
import com.example.nhamngocduc.ui.login_signup.components.ScreenLabel
import com.example.nhamngocduc.ui.login_signup.components.UsernameInput
import com.example.nhamngocduc.ui.login_signup.login.components.NewAccountSection
import com.example.nhamngocduc.ui.login_signup.login.components.RememberAndForgotSection
import com.example.nhamngocduc.util.Checker

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    var userName by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var isPasswordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    var rememberMe by rememberSaveable {
        mutableStateOf(false)
    }

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
            Spacer(modifier = Modifier.height(32.dp))

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
                onValueChanged = {
                    userName = it
                }
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Password input
            InfoTextSector(
                modifier = Modifier.fillMaxWidth(),
                isPassword = true,
                isHidden = isPasswordHidden,
                isError = false,
                imeAction = ImeAction.Done,
                errorText = "",
                value = password,
                placeholder = "Password",
                leadingIconResId = R.drawable.ic_password,
                onValueChanged = { string ->
                    password = string
                },
                onTrailingIconClick = {
                    isPasswordHidden = !isPasswordHidden
                }
            )

            RememberAndForgotSection(
                modifier = Modifier.fillMaxWidth(),
                rememberMe = rememberMe,
                onRememberMeChanged = {
                    rememberMe = it
                },
                onForgotClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))

            MainButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = Checker.checkInputsNotEmpty(userName, password),
                label = "Log in",
                onClick = onLoginClick
            )
            Spacer(modifier = Modifier.weight(1f))

            NewAccountSection(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                onSignupClick = onSignupClick
            )
        }
    }
}