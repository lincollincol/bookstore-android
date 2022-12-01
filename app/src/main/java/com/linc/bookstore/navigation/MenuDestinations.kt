package com.linc.bookstore.navigation

import com.linc.books.navigation.booksRoute
import com.linc.books.navigation.booksRouteGraph
import com.linc.cart.navigation.cartRoute
import com.linc.cart.navigation.cartRouteGraph
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.IconWrapper
import com.linc.designsystem.icon.asIconWrapper

enum class MenuDestinations(
    val icon: IconWrapper,
    val iconTextId: Int,
    val route: String
) {
    BOOKS(
        icon = BookstoreIcons.SearchBooks.asIconWrapper(),
        iconTextId = com.linc.books.R.string.books,
        route = booksRoute
    ),
    CART(
        icon = BookstoreIcons.Cart.asIconWrapper(),
        iconTextId = com.linc.cart.R.string.cart,
        route = cartRoute
    );
    companion object {
        @JvmStatic
        fun fromRoute(route: String?): MenuDestinations? =
            route?.let { values().find { dest -> dest.route.equals(it, true) } }
        @JvmStatic
        fun isTopLevelDestination(route: String?): Boolean = fromRoute(route) != null
    }
}
