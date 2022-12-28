package com.linc.bookstore.navigation

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.linc.bookdetails.navigation.bookDetailsScreen
import com.linc.bookdetails.navigation.navigateToBookDetails
import com.linc.books.navigation.booksGraph
import com.linc.books.navigation.booksRouteGraph
import com.linc.books.navigation.navigateToBooks
import com.linc.cart.navigation.cartGraph
import com.linc.cart.navigation.cartScreen
import com.linc.cart.navigation.navigateToCart
import com.linc.editsubjects.navigation.editSubjectsScreen
import com.linc.editsubjects.navigation.navigateToEditSubjects
import com.linc.navigation.navigate
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
    println(navController.backQueue.joinToString { it.destination.route.orEmpty() })
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        booksGraph(
            navigateToBookDetails = navController::navigateToBookDetails,
            navigateToSubjectBook = navController::navigateToSubjectBooks,
            nestedGraphs = {
                subjectBooksScreen(
                    navigateToBookDetails = navController::navigateToBookDetails,
                    navigateBack = navController::popBackStack
                )
                bookDetailsScreen(
                    navigateToCart = {
                        navController.navigateToCart(
                            navOptions {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        )
                    },
                    navigateToChooser = {
                        navController.navigate(intent = Intent.createChooser(it, null))
                    },
                    navigateBack = navController::popBackStack
                )
            }
        )
        cartGraph(
            navigateToBookDetails = navController::navigateToBookDetails
        )
        preferencesGraph(
            navigateToSubjectsEditor = navController::navigateToEditSubjects,
            nestedGraphs = {
                editSubjectsScreen()
            }
        )
    }
}