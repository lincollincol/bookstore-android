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
import com.linc.books.navigation.navigateToBooks
import com.linc.cart.navigation.cartGraph
import com.linc.editsubjects.navigation.editSubjectsScreen
import com.linc.editsubjects.navigation.navigateToEditSubjects
import com.linc.preferences.navigation.preferencesGraph
import com.linc.preferences.navigation.preferencesScreen
import com.linc.subjectbooks.navigation.navigateToSubjectBooks
import com.linc.subjectbooks.navigation.subjectBooksScreen

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
            navigateToSubjectBook = navController::navigateToSubjectBooks,
            nestedGraphs = {
                bookDetailsScreen(navigateBack = { navController.navigateUp() })
                subjectBooksScreen()
            }
        )
        cartGraph()
        preferencesGraph(
            navigateToSubjectsEditor = navController::navigateToEditSubjects,
            nestedGraphs = {
                editSubjectsScreen()
            }
        )
    }
}