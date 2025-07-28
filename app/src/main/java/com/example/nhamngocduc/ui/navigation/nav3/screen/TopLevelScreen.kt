package com.example.nhamngocduc.ui.navigation.nav3.screen

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface TopLevelScreen: NavKey {
    @Serializable
    data object AuthScreen : TopLevelScreen

    @Serializable
    data object MusicScreen : TopLevelScreen

    @Serializable
    data object ProfileScreen : TopLevelScreen
}