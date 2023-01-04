package com.linc.bookdetails.navigation

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.linc.bookdetails.BookDetailsRoute
import com.linc.navigation.DEEPLINK_URI
import com.linc.navigation.NavigationState
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut
import soup.compose.material.motion.navigation.composable

const val bookDetailsRoute: String = "book_details_route"
internal const val bookIdArg: String = "book_id_arg"

sealed interface BookDetailsNavigationState : NavigationState {
    object Cart : BookDetailsNavigationState
    data class Chooser(val intent: Intent) : BookDetailsNavigationState
}

internal class BookDetailsArgs(val bookId: String) {
    constructor(savedStateHandle: SavedStateHandle)
            : this(checkNotNull(savedStateHandle.get<String>(bookIdArg)))
}

fun NavController.navigateToBookDetails(
    bookId: String,
    navOptions: NavOptions? = null
) {
    navigate("$bookDetailsRoute/$bookId", navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.bookDetailsScreen(
    navigateToCart: () -> Unit,
    navigateToChooser: (Intent) -> Unit,
    navigateBack: () -> Unit
) {
    composable(
        route = "$bookDetailsRoute/{$bookIdArg}",
        arguments = listOf(
            navArgument(bookIdArg) { type = NavType.StringType }
        ),
        deepLinks = listOf(navDeepLink { uriPattern = "$DEEPLINK_URI/books/{${bookIdArg}}" }),
        enterTransition = { materialSharedAxisZIn(true, 750) },
        exitTransition = { materialSharedAxisZOut(false, 750) },
    ) {
        BookDetailsRoute(
            navigateToCart = navigateToCart,
            navigateToChooser = navigateToChooser,
            navigateBack = navigateBack
        )
    }
}