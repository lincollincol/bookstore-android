package com.linc.bookstore.navigation

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
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
import com.linc.navigation.navigate
import com.linc.preferences.navigation.preferencesGraph
import com.linc.subjectbooks.navigation.navigateToSubjectBooks
import com.linc.subjectbooks.navigation.subjectBooksScreen
import soup.compose.material.motion.navigation.MaterialMotionNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BookstoreNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = booksRouteGraph
) {
    println(navController.backQueue.joinToString { it.destination.route.orEmpty() })
    MaterialMotionNavHost(
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
            navigateToBookmarks = navController::navigateToBookmarks,
            navigateToSubjectsEditor = navController::navigateToInterests,
            navigateToLanguagePicker = navController::navigateToLanguagePicker,
            navigateToCards = navController::navigateToPayments,
            nestedGraphs = {
                paymentsScreen()
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