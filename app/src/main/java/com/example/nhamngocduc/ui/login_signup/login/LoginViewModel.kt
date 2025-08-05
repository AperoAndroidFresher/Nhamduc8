package com.example.nhamngocduc.ui.login_signup.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.manager.SessionManager
import com.example.nhamngocduc.domain.usecases.user.UserUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userUseCases: UserUseCases,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginContract.State())
    val uiState: StateFlow<LoginContract.State> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginContract.Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val scope = viewModelScope

    fun processIntent(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.ChangeInput -> changeInput(intent.inputType, intent.input)

            is LoginContract.Intent.ToggleRememberMe -> _uiState.update { it.copy(rememberMe = !it.rememberMe) }

            LoginContract.Intent.Signup -> toSignup()

            LoginContract.Intent.Login -> verifyAndLogin()
        }
    }

    private fun changeInput(inputType: LoginInputType, input: String) {
        when (inputType) {
            LoginInputType.USERNAME -> _uiState.update { it.copy(
                username = input,
                accountValidation = _uiState.value.accountValidation.copy(usernameCondition = "")
            ) }
            LoginInputType.PASSWORD -> _uiState.update { it.copy(
                password = input,
                accountValidation = _uiState.value.accountValidation.copy(passwordCondition = "")
            ) }
        }
    }

    private fun verifyAndLogin() {
        val currentState = _uiState.value

        scope.launch {
            val existingUser = userUseCases.getUser(currentState.username).firstOrNull()

            existingUser?.let {
                if (it.password == currentState.password)
                    // Assign username to session manager
                    handleSuccessfulLogin(currentState.username)
                else
                    handlePasswordNotMatch()
            } ?: run {
                handleUserNotExist()
            }
        }
    }

    private suspend fun handleSuccessfulLogin(username: String) {
        sessionManager.login(username)
        _uiEvent.emit(LoginContract.Event.NavigateToMusicGraph)
    }

    private fun handleUserNotExist() {
        _uiState.update { it.copy(
            accountValidation = LoginContract.AccountValidationState(
                usernameCondition = "Invalid user"
            )
        )}
    }

    private suspend fun handlePasswordNotMatch() {
        _uiState.update { it.copy(
            accountValidation = LoginContract.AccountValidationState(
                passwordCondition = "Invalid password"
            )
        )}
        _uiEvent.emit(LoginContract.Event.ShowToast("Invalid password"))
    }

    private fun toSignup() {
        scope.launch {
            _uiEvent.emit(LoginContract.Event.NavigateToSignup)
        }
    }
}