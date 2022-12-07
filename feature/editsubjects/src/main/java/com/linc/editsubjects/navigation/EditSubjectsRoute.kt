package com.linc.editsubjects.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.editsubjects.EditSubjectsRoute

const val editSubjectsRoute = "editsubjects_route"

fun NavController.navigateToEditSubjects(navOptions: NavOptions? = null) {
    navigate(editSubjectsRoute, navOptions)
}

fun NavGraphBuilder.editSubjectsScreen() {
    composable(editSubjectsRoute) {
        EditSubjectsRoute()
    }
}