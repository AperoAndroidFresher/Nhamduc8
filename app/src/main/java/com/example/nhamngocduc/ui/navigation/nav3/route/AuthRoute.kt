package com.example.nhamngocduc.ui.navigation.nav3.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoute : NavKey {
    @Serializable
    data object LoginRoute : AuthRoute
    @Serializable
    data object SignupRoute : AuthRoute
}