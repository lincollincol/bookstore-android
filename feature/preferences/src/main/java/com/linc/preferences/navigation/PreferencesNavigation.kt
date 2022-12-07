package com.linc.preferences.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.linc.preferences.PreferencesRoute

const val preferencesRouteGraph: String = "preferences_route_graph"
const val preferencesRoute: String = "preferences_route"

fun NavController.navigateToPreferences(navOptions: NavOptions? = null) {
    navigate(preferencesRoute, navOptions)
}

fun NavGraphBuilder.preferencesScreen() {
    composable(preferencesRoute) {
        PreferencesRoute()
    }
}

fun NavGraphBuilder.preferencesGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = preferencesRouteGraph,
        startDestination = preferencesRoute
    ) {
        preferencesScreen()
        nestedGraphs()
    }
}