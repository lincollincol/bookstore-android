package com.linc.languagepicker.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.languagepicker.LanguagePickerRoute
import com.linc.navigation.defaultAxisZEnterTransition
import com.linc.navigation.defaultAxisZExitTransition
import soup.compose.material.motion.navigation.composable

const val languagePickerRoute: String = "language_picker_route"

fun NavController.navigateToLanguagePicker(navOptions: NavOptions? = null) {
    navigate(languagePickerRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.languagePickerScreen(
    navigateBack: () -> Unit
) {
    composable(
        route = languagePickerRoute,
        enterTransition = defaultAxisZEnterTransition,
        exitTransition = defaultAxisZExitTransition
    ) {
        LanguagePickerRoute(navigateBack =navigateBack)
    }
}