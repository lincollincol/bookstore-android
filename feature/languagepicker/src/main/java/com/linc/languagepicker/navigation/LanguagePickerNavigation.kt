package com.linc.languagepicker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.languagepicker.LanguagePickerRoute

const val languagePickerRoute: String = "language_picker_route"

fun NavController.navigateToLanguagePicker(navOptions: NavOptions? = null) {
    navigate(languagePickerRoute, navOptions)
}

fun NavGraphBuilder.languagePickerScreen(
    navigateBack: () -> Unit
) {
    composable(languagePickerRoute) {
        LanguagePickerRoute(navigateBack =navigateBack)
    }
}