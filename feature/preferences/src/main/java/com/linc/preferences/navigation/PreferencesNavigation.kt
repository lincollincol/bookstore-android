package com.linc.preferences.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.linc.navigation.NavigationState
import com.linc.preferences.PreferencesRoute

const val preferencesRouteGraph: String = "preferences_route_graph"
const val preferencesRoute: String = "preferences_route"

sealed interface PreferenceNavigationState : NavigationState {
    object SubjectsEditor : PreferenceNavigationState
    object Bookmarks : PreferenceNavigationState
    object LanguagePicker : PreferenceNavigationState
}

fun NavController.navigateToPreferences(navOptions: NavOptions? = null) {
    navigate(preferencesRoute, navOptions)
}

fun NavGraphBuilder.preferencesScreen(
    navigateToBookmarks: () -> Unit,
    navigateToSubjectsEditor: () -> Unit,
    navigateToLanguagePicker: () -> Unit
) {
    composable(preferencesRoute) {
        PreferencesRoute(
            navigateToBookmarks = navigateToBookmarks,
            navigateToSubjectsEditor = navigateToSubjectsEditor,
            navigateToLanguagePicker = navigateToLanguagePicker
        )
    }
}

fun NavGraphBuilder.preferencesGraph(
    navigateToBookmarks: () -> Unit,
    navigateToSubjectsEditor: () -> Unit,
    navigateToLanguagePicker: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = preferencesRouteGraph,
        startDestination = preferencesRoute
    ) {
        preferencesScreen(
            navigateToBookmarks = navigateToBookmarks,
            navigateToSubjectsEditor = navigateToSubjectsEditor,
            navigateToLanguagePicker = navigateToLanguagePicker
        )
        nestedGraphs()
    }
}