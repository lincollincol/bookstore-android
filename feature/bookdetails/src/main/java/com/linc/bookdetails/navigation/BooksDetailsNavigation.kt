package com.linc.bookdetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.linc.bookdetails.BookDetailsRoute

const val bookDetailsRoute: String = "book_details_route"
internal const val bookIdArg: String = "book_id_arg"

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
    navigateBack: () -> Unit
) {
    composable(
        route = "$bookDetailsRoute/{$bookIdArg}",
        arguments = listOf(
            navArgument(bookIdArg) { type = NavType.StringType }
        )
    ) {
        BookDetailsRoute(
            navigateBack = navigateBack
        )
    }
}