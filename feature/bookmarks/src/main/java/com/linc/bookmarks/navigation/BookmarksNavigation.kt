package com.linc.bookmarks.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.bookmarks.BookmarksRoute
import com.linc.navigation.NavigationState
import com.linc.navigation.defaultAxisZEnterTransition
import com.linc.navigation.defaultAxisZExitTransition
import soup.compose.material.motion.navigation.composable

const val bookmarksRoute: String = "bookmarks_route"

sealed interface BookmarksNavigationState : NavigationState {
    data class BookDetails(val bookId: String) : BookmarksNavigationState
}

fun NavController.navigateToBookmarks(navOptions: NavOptions? = null) {
    navigate(bookmarksRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.bookmarksScreen(
    navigateToBookDetails: (String) -> Unit,
    navigateBack: () -> Unit
) {
    composable(
        route = bookmarksRoute,
        enterTransition = defaultAxisZEnterTransition,
        exitTransition = defaultAxisZExitTransition,
    ) {
        BookmarksRoute(
            navigateToBookDetails = navigateToBookDetails,
            navigateBack = navigateBack
        )
    }
}