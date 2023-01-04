package com.linc.editsubjects.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.linc.editsubjects.EditSubjectsRoute
import com.linc.navigation.defaultAxisZEnterTransition
import com.linc.navigation.defaultAxisZExitTransition
import soup.compose.material.motion.navigation.composable

const val interestsRoute = "interests_route"

fun NavController.navigateToInterests(navOptions: NavOptions? = null) {
    navigate(interestsRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.interestsScreen(
    navigateBack: () -> Unit
) {
    composable(
        route = interestsRoute,
        enterTransition = defaultAxisZEnterTransition,
        exitTransition = defaultAxisZExitTransition
    ) {
        EditSubjectsRoute(navigateBack = navigateBack)
    }
}