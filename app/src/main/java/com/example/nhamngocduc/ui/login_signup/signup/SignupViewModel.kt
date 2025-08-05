package com.example.nhamngocduc.ui.login_signup.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.domain.usecases.user.UserUseCases
import com.example.nhamngocduc.util.Checker
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userUseCases: UserUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupContract.State())
    val uiState: StateFlow<SignupContract.State> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SignupContract.Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val scope = viewModelScope

    fun processIntent(intent: SignupContract.Intent) {
        when (intent) {
            is SignupContract.Intent.ChangeInput -> {
                changeInput(intent.inputType, intent.input)
            }

            is SignupContract.Intent.SignUp -> {
                validateAndSignup()
            }
        }
    }

    /**
     * Change state input(s)
     */
    private fun changeInput(inputType: SignupInputType, input: String) {
        when(inputType) {
            SignupInputType.USERNAME -> _uiState.update { it.copy(
                username = input,
                accountValidation = _uiState.value.accountValidation.copy(usernameCondition = "")
            ) }

            SignupInputType.PASSWORD -> _uiState.update { it.copy(
                password = input,
                accountValidation = _uiState.value.accountValidation.copy(passwordCondition  = "", confirmPasswordCondition = ""),
            ) }

            SignupInputType.CONFIRM_PASSWORD -> _uiState.update { it.copy(
                confirmPassword = input,
                accountValidation = _uiState.value.accountValidation.copy(confirmPasswordCondition = "")
            ) }

            SignupInputType.EMAIL -> _uiState.update { it.copy(
                email = input,
                accountValidation = _uiState.value.accountValidation.copy(emailCondition = "")
            ) }
        }
    }

    /**
     * Validate and signup user
     */
    private fun validateAndSignup() {
        val currentState = _uiState.value

        var usernameCondition = ""
        var passwordCondition = ""
        var confirmPasswordCondition = ""
        var emailCondition = ""
        var isValid = true

        if (!Checker.checkUsername(currentState.username)) {
            usernameCondition = "Invalid format (min 3 chars)"
            isValid = false
        }
        if (!Checker.checkPassword(currentState.password)) {
            passwordCondition = "Invalid format (min 6 chars)"
            isValid = false
        }
        if (!Checker.isConfirmPasswordMatching(currentState.confirmPassword, currentState.password)) {
            confirmPasswordCondition = "Passwords do not match"
            isValid = false
        }

        if (!Checker.checkEmail(currentState.email) && !Checker.checkEmailTail(currentState.email)) {
            emailCondition = "Invalid email format, must have @apero.vn"
            isValid = false
        }

        _uiState.update {  it.copy(
            accountValidation = SignupContract.AccountValidationState(
                usernameCondition = usernameCondition,
                passwordCondition = passwordCondition,
                confirmPasswordCondition = confirmPasswordCondition,
                emailCondition = emailCondition
            )
        ) }

        if (isValid) {
            verifyAndSignup(currentState)
        }
    }

    /**
     * Success verify will save user to database and navigate back to login
     * Fail verify will update state with error message
     */
    private fun verifyAndSignup(state: SignupContract.State) {
        scope.launch {
            val existingUser = userUseCases.getUser(state.username).firstOrNull()

            existingUser?.let {
                // user exists
                handleUsernameTaken()
            } ?: run {
                // user does not exist -> save user
                handleSuccessfulSignup(state)
                _uiEvent.emit(SignupContract.Event.NavigateToLogin)
            }
        }
    }

    private suspend fun handleSuccessfulSignup(state: SignupContract.State) {
        userUseCases.insertUser(User(
            state.username,
            state.password,
            state.email,
        ))
    }
    private fun handleUsernameTaken() {
        _uiState.value = _uiState.value.copy(
            username = "",
            password = "",
            confirmPassword = "",
            email = "",
            accountValidation = SignupContract.AccountValidationState(
                usernameCondition = "Username already exists"
            )
        )
    }
}