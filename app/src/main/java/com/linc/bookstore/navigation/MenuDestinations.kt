package com.linc.bookstore.navigation

import com.linc.books.navigation.booksRoute
import com.linc.books.navigation.booksRouteGraph
import com.linc.cart.navigation.cartRoute
import com.linc.cart.navigation.cartRouteGraph
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.icon.IconWrapper
import com.linc.ui.icon.asIconWrapper
import com.linc.preferences.navigation.preferencesRoute

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
    ),
    PREFERENCES(
        icon = BookstoreIcons.StarOutlined.asIconWrapper(),
        iconTextId = com.linc.preferences.R.string.preferences,
        route = preferencesRoute
    );
    companion object {
        @JvmStatic
        fun fromRoute(route: String?): MenuDestinations? =
            route?.let { values().find { dest -> dest.route.equals(it, true) } }
        @JvmStatic
        fun isTopLevelDestination(route: String?): Boolean = fromRoute(route) != null
    }
}
