package com.linc.bookmarks

import com.linc.model.BookBookmark
import com.linc.model.BookOrder
import com.linc.ui.extensions.PRICE_WITH_CURRENCY_FORMAT
import com.linc.ui.state.UiState

data class BookmarksUiState(
    val bookmarks: List<BookmarkItemUiState> = emptyList()
) : UiState

data class BookmarkItemUiState(
    val bookId: String,
    val bookmarkId: String,
    val bookTitle: String,
    val bookImageUrl: String,
    val price: Double,
    val currency: String,
) : UiState

val BookmarkItemUiState.formattedPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, price, currency)

fun BookBookmark.toUiState() = BookmarkItemUiState(
    bookId = book.id,
    bookmarkId = bookmark.id,
    bookTitle = book.title,
    bookImageUrl = book.hiqImageUrl,
    price = book.price,
    currency = book.currency
)