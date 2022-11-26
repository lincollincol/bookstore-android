package com.linc.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.linc.designsystem.R

object BookstoreIcons {
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.Rounded.ArrowBack
    val ArrowDropDown = Icons.Rounded.ArrowDropDown
    val ArrowDropUp = Icons.Rounded.ArrowDropUp
    val OutlinedBookmark = R.drawable.ic_outlined_bookmark
    val Check = Icons.Rounded.Check
    val Close = Icons.Rounded.Close
    val Person = Icons.Rounded.Person
    val Search = Icons.Rounded.Search
    val Settings = Icons.Rounded.Settings
    val SearchBooks = R.drawable.ic_search_books
    val Cart = Icons.Default.ShoppingCart
}

sealed interface IconWrapper {
    data class Vector(val imageVector: ImageVector) : IconWrapper
    data class Drawable(@DrawableRes val id: Int) : IconWrapper
}
