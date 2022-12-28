package com.linc.ui.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.linc.ui.R

object BookstoreIcons {
    val Add = Icons.Rounded.Add
    val Remove = Icons.Rounded.Remove
    val BookNotFound = R.drawable.ic_book_not_found
    val Star = Icons.Default.Star
    val StarOutlined = Icons.Outlined.StarOutline
    val ArrowBack = Icons.Rounded.ArrowBack
    val ArrowDropDown = Icons.Rounded.ArrowDropDown
    val MoreVertical = Icons.Default.MoreVert
    val ArrowDropUp = Icons.Rounded.ArrowDropUp
    val OutlinedBookmark = R.drawable.ic_outlined_bookmark
    val BookmarkAdd = Icons.Default.BookmarkAdd
    val BookmarkAdded = Icons.Default.BookmarkAdded
    val Check = Icons.Rounded.Check
    val Close = Icons.Rounded.Close
    val Clear = Icons.Rounded.Clear
    val Person = Icons.Rounded.Person
    val SoldOut = R.drawable.ic_sold_out
    val Search = Icons.Rounded.Search
    val Share = Icons.Rounded.Share
    val Settings = Icons.Rounded.Settings
    val SearchBooks = R.drawable.ic_search_books
    val Cart = Icons.Default.ShoppingCart
}

sealed interface IconWrapper {
    data class Vector(val imageVector: ImageVector) : IconWrapper
    data class Drawable(@DrawableRes val id: Int) : IconWrapper
}

fun ImageVector.asIconWrapper() = IconWrapper.Vector(this)

fun Int.asIconWrapper() = IconWrapper.Drawable(this)

fun IconWrapper?.buildComposable(
    icon: @Composable (IconWrapper) -> Unit
) : @Composable (() -> Unit)? = this?.let { { icon(it) } }