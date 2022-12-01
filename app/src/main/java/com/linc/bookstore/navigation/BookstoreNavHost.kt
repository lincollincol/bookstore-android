package com.linc.bookstore.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.linc.bookdetails.navigation.bookDetailsScreen
import com.linc.bookdetails.navigation.navigateToBookDetails
import com.linc.books.navigation.booksGraph
import com.linc.books.navigation.booksRouteGraph
import com.linc.cart.navigation.cartGraph

@Composable
fun BookstoreNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = booksRouteGraph
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        booksGraph(
            navigateToBookDetails = navController::navigateToBookDetails,
            nestedGraphs = {
                bookDetailsScreen()
            }
        )
        cartGraph()
    }
}