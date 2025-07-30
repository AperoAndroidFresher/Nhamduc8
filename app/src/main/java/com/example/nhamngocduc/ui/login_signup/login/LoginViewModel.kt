package com.example.nhamngocduc.ui.login_signup.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginContract.State())
    val uiState: StateFlow<LoginContract.State> = _uiState.asStateFlow()

    fun processIntent(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.ChangeInput -> {
                when (intent.inputType) {
                    LoginInputType.USERNAME -> _uiState.update { it.copy(username = intent.input) }
                    LoginInputType.PASSWORD -> _uiState.update { it.copy(password = intent.input) }
                }
            }

            is LoginContract.Intent.ToggleRememberMe -> _uiState.update { it.copy(rememberMe = !it.rememberMe) }

            LoginContract.Intent.SignUp -> {

            }
        }
    }
}