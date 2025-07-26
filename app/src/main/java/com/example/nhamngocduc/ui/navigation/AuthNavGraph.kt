package com.example.nhamngocduc.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.nhamngocduc.ui.login_signup.login.LoginScreen
import com.example.nhamngocduc.ui.login_signup.signup.SignUpScreen

fun NavGraphBuilder.AuthNavGraph(
    navController: NavController
) {
    navigation(
        route = Route.AuthenticationScreen.route,
        startDestination = Route.LoginScreen.route
    ) {
        composable(
            route = Route.LoginScreen.route,
            enterTransition = { slideInHorizontally(tween(250)) { it -> it } },
            exitTransition = { slideOutHorizontally(tween(250)) { it -> -it } },
            popEnterTransition = { slideInHorizontally(tween(250)) { it -> -it } }
        ) {
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                onLoginClick = { },
                onSignupClick = {
                    navController.navigate(Route.SignupScreen.route)
                }
            )
        }
        composable(
            route = Route.SignupScreen.route,
            enterTransition = { slideInHorizontally(tween(250)) { it -> it } },
            exitTransition = { slideOutHorizontally(tween(250)) { it -> -it } },
            popExitTransition = { slideOutHorizontally(tween(250)) { it -> it } }
        ) {
            SignUpScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateBack = {
                    navController.navigateUp()
                },
                onSignUp = {
                    navController.navigate(Route.LoginScreen.route)
                }
            )
        }
    }
}