package com.linc.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import com.linc.ui.R

val MaterialTheme.icons: BookstoreIcons
    @Composable
    @ReadOnlyComposable
    get() = LocalIconProvider.current

val LocalIconProvider = staticCompositionLocalOf {
    defaultBookstoreIcons
}

val defaultBookstoreIcons = BookstoreIcons(
    add = Icons.Rounded.Add.asIconWrapper(),
    arrowBack = Icons.Rounded.ArrowBack.asIconWrapper(),
    remove = Icons.Rounded.Remove.asIconWrapper(),
    star = Icons.Default.Star.asIconWrapper(),
    starOutlined = Icons.Outlined.StarOutline.asIconWrapper(),
    arrowForwardIos = Icons.Default.ArrowForwardIos.asIconWrapper(),
    arrowDropDown = Icons.Rounded.ArrowDropDown.asIconWrapper(),
    moreVertical = Icons.Default.MoreVert.asIconWrapper(),
    arrowDropUp = Icons.Rounded.ArrowDropUp.asIconWrapper(),
    interests = Icons.Default.Interests.asIconWrapper(),
    language = Icons.Default.Language.asIconWrapper(),
    bookmarkAdd = Icons.Default.BookmarkAdd.asIconWrapper(),
    bookmarkAdded = Icons.Default.BookmarkAdded.asIconWrapper(),
    bookmarkRemove = Icons.Default.BookmarkRemove.asIconWrapper(),
    check = Icons.Rounded.Check.asIconWrapper(),
    close = Icons.Rounded.Close.asIconWrapper(),
    clear = Icons.Rounded.Clear.asIconWrapper(),
    person = Icons.Rounded.Person.asIconWrapper(),
    search = Icons.Rounded.Search.asIconWrapper(),
    share = Icons.Rounded.Share.asIconWrapper(),
    settings = Icons.Rounded.Settings.asIconWrapper(),
    cart = Icons.Default.ShoppingCart.asIconWrapper(),
    outlinedBookmark = R.drawable.ic_outlined_bookmark.asIconWrapper(),
    bookNotFound = R.drawable.ic_book_not_found.asIconWrapper(),
    soldOut = R.drawable.ic_sold_out.asIconWrapper(),
    searchBooks = R.drawable.ic_search_books.asIconWrapper(),
)

data class BookstoreIcons(
    val add: IconWrapper = Icons.Rounded.Add.asIconWrapper(),
    val arrowBack: IconWrapper = Icons.Rounded.ArrowBack.asIconWrapper(),
    val remove: IconWrapper = Icons.Rounded.Remove.asIconWrapper(),
    val star: IconWrapper = Icons.Default.Star.asIconWrapper(),
    val starOutlined: IconWrapper = Icons.Outlined.StarOutline.asIconWrapper(),
    val arrowForwardIos: IconWrapper = Icons.Default.ArrowForwardIos.asIconWrapper(),
    val arrowDropDown: IconWrapper = Icons.Rounded.ArrowDropDown.asIconWrapper(),
    val moreVertical: IconWrapper = Icons.Default.MoreVert.asIconWrapper(),
    val arrowDropUp: IconWrapper = Icons.Rounded.ArrowDropUp.asIconWrapper(),
    val interests: IconWrapper = Icons.Default.Interests.asIconWrapper(),
    val language: IconWrapper = Icons.Default.Language.asIconWrapper(),
    val addCard: IconWrapper = Icons.Default.AddCard.asIconWrapper(),
    val bookmarkAdd: IconWrapper = Icons.Default.BookmarkAdd.asIconWrapper(),
    val bookmarkAdded: IconWrapper = Icons.Default.BookmarkAdded.asIconWrapper(),
    val bookmarkRemove: IconWrapper = Icons.Default.BookmarkRemove.asIconWrapper(),
    val check: IconWrapper = Icons.Rounded.Check.asIconWrapper(),
    val close: IconWrapper = Icons.Rounded.Close.asIconWrapper(),
    val clear: IconWrapper = Icons.Rounded.Clear.asIconWrapper(),
    val person: IconWrapper = Icons.Rounded.Person.asIconWrapper(),
    val search: IconWrapper = Icons.Rounded.Search.asIconWrapper(),
    val share: IconWrapper = Icons.Rounded.Share.asIconWrapper(),
    val settings: IconWrapper = Icons.Rounded.Settings.asIconWrapper(),
    val cart: IconWrapper = Icons.Default.ShoppingCart.asIconWrapper(),
    val outlinedBookmark: IconWrapper = R.drawable.ic_outlined_bookmark.asIconWrapper(),
    val bookNotFound: IconWrapper = R.drawable.ic_book_not_found.asIconWrapper(),
    val soldOut: IconWrapper = R.drawable.ic_sold_out.asIconWrapper(),
    val searchBooks: IconWrapper = R.drawable.ic_search_books.asIconWrapper(),
)

sealed interface IconWrapper {
    data class Vector(val imageVector: ImageVector) : IconWrapper
    data class Drawable(@DrawableRes val id: Int) : IconWrapper
}

private fun ImageVector.asIconWrapper() = IconWrapper.Vector(this)

private fun Int.asIconWrapper() = IconWrapper.Drawable(this)

fun IconWrapper.unwrapDrawable(): Int? = (this as? IconWrapper.Drawable)?.id

fun IconWrapper.unwrapVector(): ImageVector? = (this as? IconWrapper.Vector)?.imageVector