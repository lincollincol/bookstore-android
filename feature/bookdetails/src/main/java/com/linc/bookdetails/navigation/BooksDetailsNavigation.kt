package com.linc.bookdetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.linc.bookdetails.BookDetailsRoute
import com.linc.navigation.NavigationState

const val bookDetailsRoute: String = "book_details_route"
internal const val bookIdArg: String = "book_id_arg"

sealed interface BookDetailsNavigationState : NavigationState {
    object Cart : BookDetailsNavigationState
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

fun NavGraphBuilder.bookDetailsScreen(
    navigateToCart: () -> Unit,
    navigateBack: () -> Unit
) {
    composable(
        route = "$bookDetailsRoute/{$bookIdArg}",
        arguments = listOf(
            navArgument(bookIdArg) { type = NavType.StringType }
        )
    ) {
        BookDetailsRoute(
            navigateToCart = navigateToCart,
            navigateBack = navigateBack
        )
    }
}