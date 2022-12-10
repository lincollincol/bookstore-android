package com.linc.books.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.linc.books.BooksRoute
import com.linc.navigation.NavigationState

const val booksRouteGraph = "books_route_graph"
const val booksRoute = "books_route"

sealed interface BooksNavigationState : NavigationState{
    data class BookDetails(val bookId: String) : BooksNavigationState
    data class SubjectBooks(val subjectId: String) : BooksNavigationState
}

fun NavController.navigateToBooks(navOptions: NavOptions? = null) {
    this.navigate(booksRoute, navOptions)
}

fun NavGraphBuilder.booksScreen(
    navigateToBookDetails: (String) -> Unit,
    navigateToSubjectBook: (String) -> Unit,
) {
    composable(route = booksRoute) {
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
