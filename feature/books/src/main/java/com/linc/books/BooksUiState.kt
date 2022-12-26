package com.linc.books

import com.linc.ui.state.UiState
import com.linc.model.Book
import com.linc.model.SubjectBooks
import com.linc.ui.model.DetailedBookItemUiState

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
    val availableForSale: Boolean,
    val averageRating: Float,
    val ratingsCount: Float,
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
    availableForSale = availableForSale,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    title = title
)

fun Book.toDetailedItemUiState() = DetailedBookItemUiState(
    id = id,
    imageUrl = imageUrl,
    price = price,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    title = title,
    authors = authors.joinToString()
)

val BooksUiState.isSearching: Boolean get() = searchQuery.isNotEmpty()