package com.example.nhamngocduc.ui.login_signup.signup

object SignupContract {
    data class State(
        val username: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val email: String = "",
        val accountValidation: AccountValidationState = AccountValidationState()
    )

    data class AccountValidationState(
        val usernameCondition: String = "",
        val passwordCondition: String = "",
        val confirmPasswordCondition: String = "",
        val emailCondition: String = ""
    )

    sealed interface Intent {
        data class ChangeInput(val input: String, val inputType: SignupInputType) : Intent
        data class SignUp(val nav: () -> Unit) : Intent
    }

    sealed interface Event {
        data object SignUpSuccess: Event
    }
}

enum class SignupInputType {
    USERNAME,
    PASSWORD,
    CONFIRM_PASSWORD,
    EMAIL
}