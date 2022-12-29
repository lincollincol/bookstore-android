package com.linc.bookmarks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.bookmarks.BookmarksRoute
import com.linc.navigation.NavigationState

const val bookmarksRoute: String = "bookmarks_route"

sealed interface BookmarksNavigationState : NavigationState {
    data class BookDetails(val bookId: String) : BookmarksNavigationState
}

fun NavController.navigateToBookmarks(navOptions: NavOptions? = null) {
    navigate(bookmarksRoute, navOptions)
}

fun NavGraphBuilder.bookmarksScreen(
    navigateToBookDetails: (String) -> Unit,
    navigateBack: () -> Unit
) {
    composable(bookmarksRoute) {
        BookmarksRoute(
            navigateToBookDetails = navigateToBookDetails,
            navigateBack = navigateBack
        )
    }
}