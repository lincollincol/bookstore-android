package com.linc.bookstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.linc.bookdetails.navigation.bookDetailsScreen
import com.linc.bookdetails.navigation.navigateToBookDetails
import com.linc.books.navigation.booksGraph
import com.linc.books.navigation.booksGraphRoute
import com.linc.books.navigation.booksScreen

@Composable
fun BookstoreNavHost(
    navController: NavHostController,
    startDestination: String = booksGraphRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        booksGraph(
            navigateToBookDetails = navController::navigateToBookDetails,
            nestedGraphs = {
                bookDetailsScreen()
            }
        )
    }
}