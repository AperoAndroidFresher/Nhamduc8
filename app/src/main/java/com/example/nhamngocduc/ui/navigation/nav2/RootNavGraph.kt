package com.example.nhamngocduc.ui.navigation.nav2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        AuthNavGraph(navController)
    }
}

fun NavController.navigateClearStack(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateClearStack.graph.findStartDestination().id
        )
    }
}