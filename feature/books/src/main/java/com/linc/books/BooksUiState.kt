package com.linc.books

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.linc.ui.state.UiState
import com.linc.model.Book
import com.linc.model.SubjectBooks
import com.linc.ui.extensions.ONE_DECIMAL_DIGITS_FORMAT
import com.linc.ui.extensions.PRICE_WITH_CURRENCY_FORMAT
import com.linc.ui.extensions.TWO_DECIMAL_DIGITS_FORMAT
import com.linc.ui.extensions.ZERO_DECIMAL_DIGITS_FORMAT
import com.linc.ui.model.DetailedBookItemUiState
import com.linc.ui.theme.strings

data class BooksUiState(
    val books: List<BooksSectionItemUiState> = emptyList(),
    val searchQuery: String = ""
) : UiState

data class BooksSectionItemUiState(
    val subjectId: String,
    val title: String,
    val books: List<BookItemUiState>
) : UiState

data class BookItemUiState(
    val id: String,
    val imageUrl: String,
    val price: Double,
    val currency: String,
    val availableForSale: Boolean,
    val averageRating: Float,
    val ratingsCount: Int,
    val title: String
) : UiState

internal fun SubjectBooks.toUiState() = BooksSectionItemUiState(
    subjectId = subject.id,
    title = subject.name,
    books = books.map(Book::toUiState)
)

internal fun Book.toUiState() = BookItemUiState(
    id = id,
    imageUrl = imageUrl,
    price = price,
    currency = currency,
    availableForSale = availableForSale,
    averageRating = averageRating,
    ratingsCount = ratingsCount.toInt(),
    title = title
)

fun Book.toDetailedItemUiState() = DetailedBookItemUiState(
    id = id,
    imageUrl = imageUrl,
    price = price,
    currency = currency,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    title = title,
    authors = authors.joinToString(),
    isAvailable = availableForSale
)

val BooksUiState.isSearching: Boolean get() = searchQuery.isNotEmpty()

val BookItemUiState.formattedRating: String
    get() = when(averageRating % 1f) {
        0f -> ZERO_DECIMAL_DIGITS_FORMAT
        else -> ONE_DECIMAL_DIGITS_FORMAT
    }.format(averageRating)

val BookItemUiState.formattedPrice: String get() =
    PRICE_WITH_CURRENCY_FORMAT.format(price, currency)