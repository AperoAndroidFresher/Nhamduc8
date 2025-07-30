package com.example.nhamngocduc.ui.navigation.nav3

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.nhamngocduc.ui.login_signup.components.TopBar
import com.example.nhamngocduc.ui.login_signup.login.LoginScreen
import com.example.nhamngocduc.ui.login_signup.login.LoginViewModel
import com.example.nhamngocduc.ui.login_signup.signup.SignUpScreen
import com.example.nhamngocduc.ui.navigation.nav3.route.AuthRoute

@Composable
fun AuthNavGraph(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    val backStack = rememberNavBackStack<AuthRoute>(AuthRoute.LoginRoute)

    val focusManager = LocalFocusManager.current

    var topBarState by rememberSaveable { mutableStateOf(true) }

    topBarState = backStack.lastOrNull() == AuthRoute.SignupRoute

    Scaffold(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                }) }
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth().padding(horizontal =  16.dp),
                topBarState = topBarState,
                onBackClick = {backStack.removeLastOrNull()}
            )
        }
    ) { paddingValues ->
        NavDisplay(
            modifier = Modifier.fillMaxSize(),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf (
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<AuthRoute.LoginRoute> {
                    val viewModel: LoginViewModel = viewModel()

                    LoginScreen(
                        modifier = Modifier.padding(paddingValues),
                        viewModel = viewModel,
                        onLoginClick = onLoginClick,
                        onSignupClick = { backStack.add(AuthRoute.SignupRoute) },
                    )
                }
                entry<AuthRoute.SignupRoute> {
                    SignUpScreen(
                        modifier = Modifier.padding(paddingValues),
                        onSignUp = { backStack.removeLastOrNull() }
                    )
                }
            },
            transitionSpec = {
                slideInHorizontally(tween(250), initialOffsetX = { it }) togetherWith
                        slideOutHorizontally(tween(250),targetOffsetX = { -it })
            },
            popTransitionSpec = {
                slideInHorizontally(tween(250),initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(tween(250),targetOffsetX = { it })
            },
        )
    }
}