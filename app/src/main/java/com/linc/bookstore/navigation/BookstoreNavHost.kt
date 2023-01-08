package com.linc.bookstore.navigation

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.linc.auth.navigation.authRoute
import com.linc.auth.navigation.authScreen
import com.linc.bookdetails.navigation.bookDetailsScreen
import com.linc.bookdetails.navigation.navigateToBookDetails
import com.linc.bookmarks.navigation.bookmarksScreen
import com.linc.bookmarks.navigation.navigateToBookmarks
import com.linc.books.navigation.booksGraph
import com.linc.books.navigation.booksRouteGraph
import com.linc.payments.navigation.navigateToPayments
import com.linc.payments.navigation.paymentsScreen
import com.linc.cart.navigation.cartGraph
import com.linc.cart.navigation.navigateToCart
import com.linc.interests.navigation.interestsScreen
import com.linc.interests.navigation.navigateToInterests
import com.linc.languagepicker.navigation.languagePickerScreen
import com.linc.languagepicker.navigation.navigateToLanguagePicker
import com.linc.navigation.defaultAxisZEnterTransition
import com.linc.navigation.defaultAxisZExitTransition
import com.linc.navigation.navigate
import com.linc.preferences.navigation.preferencesGraph
import com.linc.subjectbooks.navigation.navigateToSubjectBooks
import com.linc.subjectbooks.navigation.subjectBooksScreen
import soup.compose.material.motion.navigation.MaterialMotionNavHost
import soup.compose.material.motion.navigation.composable

private const val mainRouteGraph = "main_route_graph"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BookstoreNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isAuthorizedUser: Boolean
) {
    val startDestination = remember(isAuthorizedUser) {
        if(isAuthorizedUser) mainRouteGraph else authRoute
    }
    MaterialMotionNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        authScreen(
            navigateToMain = navController::navigateToMain,
            navigateToInterests = navController::navigateToInterests
        )
        navigation(
            route = mainRouteGraph,
            startDestination = booksRouteGraph
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
                navigateToBookmarks = navController::navigateToBookmarks,
                navigateToSubjectsEditor = navController::navigateToInterests,
                navigateToLanguagePicker = navController::navigateToLanguagePicker,
                navigateToPayments = navController::navigateToPayments,
                nestedGraphs = {
                    paymentsScreen(navigateBack = navController::popBackStack)
                    interestsScreen(navigateBack = navController::popBackStack)
                    languagePickerScreen(navigateBack = navController::popBackStack)
                    bookmarksScreen(
                        navigateToBookDetails = navController::navigateToBookDetails,
                        navigateBack = navController::popBackStack
                    )
                }
            )
        }
    }
}

private fun NavController.navigateToMain() {
    navigate(mainRouteGraph) {
        popUpTo(graph.id) { inclusive = true }
    }
}