package com.linc.preferences.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.linc.navigation.NavigationState
import com.linc.navigation.defaultHostEnterTransition
import com.linc.navigation.defaultHostExitTransition
import com.linc.preferences.PreferencesRoute
import soup.compose.material.motion.navigation.composable

const val preferencesRouteGraph: String = "preferences_route_graph"
const val preferencesRoute: String = "preferences_route"

sealed interface PreferenceNavigationState : NavigationState {
    object SubjectsEditor : PreferenceNavigationState
    object Bookmarks : PreferenceNavigationState
    object LanguagePicker : PreferenceNavigationState
    object Cards : PreferenceNavigationState
}

fun NavController.navigateToPreferences(navOptions: NavOptions? = null) {
    navigate(preferencesRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.preferencesScreen(
    navigateToBookmarks: () -> Unit,
    navigateToSubjectsEditor: () -> Unit,
    navigateToLanguagePicker: () -> Unit,
    navigateToCards: () -> Unit
) {
    composable(
        route = preferencesRoute,
        enterTransition = defaultHostEnterTransition,
        exitTransition = defaultHostExitTransition
    ) {
        PreferencesRoute(
            navigateToBookmarks = navigateToBookmarks,
            navigateToSubjectsEditor = navigateToSubjectsEditor,
            navigateToLanguagePicker = navigateToLanguagePicker,
            navigateToCards = navigateToCards
        )
    }
}

fun NavGraphBuilder.preferencesGraph(
    navigateToBookmarks: () -> Unit,
    navigateToSubjectsEditor: () -> Unit,
    navigateToLanguagePicker: () -> Unit,
    navigateToCards: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = preferencesRouteGraph,
        startDestination = preferencesRoute
    ) {
        preferencesScreen(
            navigateToBookmarks = navigateToBookmarks,
            navigateToSubjectsEditor = navigateToSubjectsEditor,
            navigateToLanguagePicker = navigateToLanguagePicker,
            navigateToCards = navigateToCards
        )
        nestedGraphs()
    }
}