package com.example.nhamngocduc.ui.login_signup.login


object LoginContract {
    data class State(
        val username: String = "",
        val password: String = "",
        val accountValidation: AccountValidationState = AccountValidationState(),
        val rememberMe: Boolean = false,
    )

    data class AccountValidationState(
        val usernameCondition: String = "",
        val passwordCondition: String = ""
    ) {
        val isValidated: Boolean = usernameCondition.isEmpty() && passwordCondition.isEmpty()
    }

    sealed interface Intent {
        data class ChangeInput(val input: String, val inputType: LoginInputType) : Intent
        data object ToggleRememberMe : Intent
        data object Signup : Intent
        data object Login : Intent
    }

    sealed interface Event {
        data class ShowToast(val message: String) : Event
        data object NavigateToSignup: Event
        data object NavigateToMusicGraph: Event
    }
}

enum class LoginInputType {
    USERNAME,
    PASSWORD,
}