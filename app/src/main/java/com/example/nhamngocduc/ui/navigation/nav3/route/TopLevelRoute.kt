package com.example.nhamngocduc.ui.navigation.nav3.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface TopLevelRoute: NavKey {
    @Serializable
    data object AuthRoute : TopLevelRoute

    @Serializable
    data object MusicRoute : TopLevelRoute

    @Serializable
    data object ProfileRoute : TopLevelRoute
}