package com.linc.books.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.*
import com.linc.books.BooksRoute
import com.linc.navigation.*
import soup.compose.material.motion.navigation.composable

const val booksRouteGraph = "books_route_graph"
const val booksRoute = "books_route"

sealed interface BooksNavigationState : NavigationState{
    data class BookDetails(val bookId: String) : BooksNavigationState
    data class SubjectBooks(val subjectId: String) : BooksNavigationState
}

fun NavController.navigateToBooks(navOptions: NavOptions? = null) {
    this.navigate(booksRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.booksScreen(
    navigateToBookDetails: (String) -> Unit,
    navigateToSubjectBook: (String) -> Unit,
) {
    composable(
        route = booksRoute,
        deepLinks = listOf(navDeepLink { uriPattern = "$DEEPLINK_URI/books" }),
        enterTransition = defaultHostEnterTransition,
        exitTransition = defaultHostExitTransition
    ) {
        BooksRoute(navigateToBookDetails, navigateToSubjectBook)
    }
}

fun NavGraphBuilder.booksGraph(
    navigateToBookDetails: (String) -> Unit,
    navigateToSubjectBook: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit = {}
) {
    navigation(
        route = booksRouteGraph,
        startDestination = booksRoute
    ) {
        booksScreen(
            navigateToBookDetails,
            navigateToSubjectBook
        )
        nestedGraphs()
    }
}
