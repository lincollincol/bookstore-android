package com.linc.books.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.books.BooksRoute

const val booksNavRoute = "books"

fun NavController.navigateToBooks(navOptions: NavOptions? = null) {
    this.navigate(booksNavRoute, navOptions)
}

fun NavGraphBuilder.booksScreen() {
    composable(route = booksNavRoute) {
        BooksRoute()
    }
}
