package com.example.nhamngocduc.ui.login_signup.signup

import androidx.lifecycle.ViewModel
import com.example.nhamngocduc.util.Checker
import com.example.nhamngocduc.util.Users.usersMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignupContract.State())
    val uiState: StateFlow<SignupContract.State> = _uiState.asStateFlow()

    fun processIntent(intent: SignupContract.Intent) {
        when (intent) {
            is SignupContract.Intent.ChangeInput -> {
                when(intent.inputType) {
                    SignupInputType.USERNAME -> _uiState.update { it.copy(username = intent.input) }
                    SignupInputType.PASSWORD -> _uiState.update { it.copy(password = intent.input) }
                    SignupInputType.CONFIRM_PASSWORD -> _uiState.update { it.copy(confirmPassword = intent.input) }
                    SignupInputType.EMAIL -> _uiState.update { it.copy(email = intent.input) }
                }
            }

            SignupContract.Intent.SignUp -> {
                val currentState = _uiState.value

                var usernameCondition: String = ""
                var passwordCondition: String = ""
                var confirmPasswordCondition: String = ""
                var emailCondition: String = ""
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

                if (!Checker.checkEmail(currentState.email) && Checker.checkEmailTail(currentState.email)) {
                    emailCondition = "Invalid email format, must have @apero.vn"
                    isValid = false
                }

                _uiState.value = _uiState.value.copy(
                    accountValidation = SignupContract.AccountValidationState(
                        usernameCondition = usernameCondition,
                        passwordCondition = passwordCondition,
                        confirmPasswordCondition = confirmPasswordCondition,
                        emailCondition = emailCondition
                    )
                )

                if (isValid) {
                    if (usersMap.containsKey(currentState.username)) {
                        _uiState.value = _uiState.value.copy(
                            accountValidation = SignupContract.AccountValidationState(
                                usernameCondition = "Username already exists"
                            )
                        )
                    } else {

                    }
                }
            }
        }
    }

    private fun validateAndSignUp() {

    }
}