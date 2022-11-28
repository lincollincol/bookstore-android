package com.linc.bookstore.navigation

import com.linc.bookstore.R
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.IconWrapper
import com.linc.designsystem.icon.asIconWrapper

enum class MenuDestinations(
    val icon: IconWrapper,
    val iconTextId: Int
) {
    BOOKS(
        icon = BookstoreIcons.SearchBooks.asIconWrapper(),
        iconTextId = com.linc.books.R.string.books
    ),
    CART(
        icon = BookstoreIcons.Cart.asIconWrapper(),
        iconTextId = com.linc.cart.R.string.cart
    )
}
