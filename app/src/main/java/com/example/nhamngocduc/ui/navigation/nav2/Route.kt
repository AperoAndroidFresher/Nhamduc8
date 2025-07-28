package com.example.nhamngocduc.ui.navigation.nav2

sealed class Route(
    val route: String
) {
    data object AuthenticationScreen: Route("auth_navigation")
    data object LoginScreen : Route("login_screen")
    data object SignupScreen : Route("signup_screen")
}