package com.linc.bookstore.navigation

import com.linc.bookstore.R
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.IconWrapper

enum class MenuDestinations(
    val icon: IconWrapper,
    val iconTextId: Int
) {
    BOOKS(
        icon = IconWrapper.Drawable(BookstoreIcons.SearchBooks),
        iconTextId = com.linc.books.R.string.books
    ),
    CART(
        icon = IconWrapper.Vector(BookstoreIcons.Cart),
        iconTextId = com.linc.cart.R.string.cart
    )
}
