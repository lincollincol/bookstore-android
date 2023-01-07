package com.linc.auth.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.linc.auth.AuthRoute
import com.linc.navigation.NavigationState
import com.linc.navigation.defaultAxisZEnterTransition
import com.linc.navigation.defaultAxisZExitTransition
import soup.compose.material.motion.navigation.composable

const val authRoute: String = "auth_route"

sealed interface AuthNavigationState : NavigationState {
    object Main : AuthNavigationState
    object Interests : AuthNavigationState
}

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    navigate(authRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authScreen(
    navigateToMain: () -> Unit,
    navigateToInterests: () -> Unit
) {
    composable(
        route = authRoute,
        enterTransition = defaultAxisZEnterTransition,
        exitTransition = defaultAxisZExitTransition
    ) {
        AuthRoute(
            navigateToMain = navigateToMain,
            navigateToInterests = navigateToInterests
        )
    }
}
