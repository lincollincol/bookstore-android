package com.linc.books.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.linc.books.BooksRoute

const val booksRouteGraph = "books_route_graph"
const val booksRoute = "books_route"

fun NavController.navigateToBooks(navOptions: NavOptions? = null) {
    this.navigate(booksRoute, navOptions)
}

fun NavGraphBuilder.booksScreen(
    navigateToBookDetails: (String) -> Unit
) {
    composable(route = booksRoute) {
        BooksRoute(navigateToBookDetails)
    }
}

fun NavGraphBuilder.booksGraph(
    navigateToBookDetails: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit = {}
) {
    navigation(
        route = booksRouteGraph,
        startDestination = booksRoute
    ) {
        booksScreen(navigateToBookDetails)
        nestedGraphs()
    }
}
