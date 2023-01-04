package com.linc.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.auth.AuthRoute

const val authRoute: String = "auth_route"

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    navigate(authRoute, navOptions)
}

fun NavGraphBuilder.authScreen() {
    composable(authRoute) {
        AuthRoute()
    }
}