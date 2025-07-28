package com.example.nhamngocduc.ui.navigation.nav3.screen

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthScreen : NavKey {
    @Serializable
    data object LoginScreen : AuthScreen
    @Serializable
    data object SignupScreen : AuthScreen
}